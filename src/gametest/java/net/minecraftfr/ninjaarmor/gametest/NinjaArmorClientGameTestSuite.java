package net.minecraftfr.ninjaarmor.gametest;

import com.mojang.authlib.GameProfile;
import io.netty.channel.embedded.EmbeddedChannel;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraftfr.ninjaarmor.item.ModItems;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

public class NinjaArmorClientGameTestSuite implements FabricClientGameTest {

	@Override
	public void runTest(ClientGameTestContext context) {
		testNameTagHiddenWithFullNinjaArmor(context);
		testNameTagVisibleWithoutArmor(context);
	}

	private void testNameTagHiddenWithFullNinjaArmor(ClientGameTestContext context) {
		try (TestSingleplayerContext world = context.worldBuilder().create()) {
			spawnMockPlayerInFront(world, context, "TargetNinja", true);
			assertScreenshotMatches(context, "name_tag_hidden_with_ninja_armor");
		}
	}

	private void testNameTagVisibleWithoutArmor(ClientGameTestContext context) {
		try (TestSingleplayerContext world = context.worldBuilder().create()) {
			spawnMockPlayerInFront(world, context, "TargetPlayer", false);
			assertScreenshotMatches(context, "name_tag_visible_without_armor");
		}
	}

	private static void assertScreenshotMatches(ClientGameTestContext context, String screenshotName) {
		context.takeScreenshot(screenshotName);

		context.runOnClient(mc -> {
			Path screenshotsDir = mc.gameDirectory.toPath().resolve("screenshots");

			Path screenshotFile;
			try (Stream<Path> files = Files.list(screenshotsDir)) {
				screenshotFile = files
					.filter(p -> p.getFileName().toString().endsWith("_" + screenshotName + ".png"))
					.max(Comparator.comparingLong(p -> p.toFile().lastModified()))
					.orElseThrow(() -> new AssertionError("Screenshot introuvable : " + screenshotName));
			} catch (IOException e) {
				throw new RuntimeException("Impossible de lire le dossier screenshots", e);
			}

			BufferedImage actual;
			try {
				actual = ImageIO.read(screenshotFile.toFile());
			} catch (IOException e) {
				throw new RuntimeException("Impossible de lire le screenshot : " + screenshotFile, e);
			}

			BufferedImage reference;
			try (InputStream in = NinjaArmorClientGameTestSuite.class
					.getResourceAsStream("/screenshots/" + screenshotName + ".png")) {
				if (in == null) {
					throw new AssertionError("Screenshot de référence manquant : /screenshots/" + screenshotName + ".png");
				}
				reference = ImageIO.read(in);
			} catch (IOException e) {
				throw new RuntimeException("Impossible de lire le screenshot de référence", e);
			}

			compareNameTagZone(screenshotName, reference, actual);
		});
	}

	/**
	 * Compare uniquement la zone du name tag (ciel + label, au-dessus du corps du joueur).
	 * Exclut le corps (animation des bras) et le HUD/chat en bas.
	 * Zone : 15%–40% vertical, 25%–75% horizontal (centré).
	 */
	private static void compareNameTagZone(String name, BufferedImage reference, BufferedImage actual) {
		if (reference.getWidth() != actual.getWidth() || reference.getHeight() != actual.getHeight()) {
			throw new AssertionError(String.format(
				"Screenshot '%s' : dimensions différentes (référence %dx%d, actuel %dx%d)",
				name, reference.getWidth(), reference.getHeight(), actual.getWidth(), actual.getHeight()
			));
		}

		int width = reference.getWidth();
		int height = reference.getHeight();
		int x1 = width / 4,          x2 = 3 * width / 4;
		int y1 = (int)(height * 0.15), y2 = (int)(height * 0.40);
		int roiPixels = (x2 - x1) * (y2 - y1);
		int diffPixels = 0;

		for (int y = y1; y < y2; y++) {
			for (int x = x1; x < x2; x++) {
				if (!pixelsClose(reference.getRGB(x, y), actual.getRGB(x, y))) {
					diffPixels++;
				}
			}
		}

		double diffRatio = (double) diffPixels / roiPixels;
		if (diffRatio > 0.01) {
			throw new AssertionError(String.format(
				"Screenshot '%s' : %.1f%% des pixels de la zone name tag diffèrent de la référence (seuil : 1%%)",
				name, diffRatio * 100
			));
		}
	}

	private static boolean pixelsClose(int rgb1, int rgb2) {
		int threshold = 10;
		return Math.abs(((rgb1 >> 16) & 0xFF) - ((rgb2 >> 16) & 0xFF)) <= threshold
			&& Math.abs(((rgb1 >> 8) & 0xFF) - ((rgb2 >> 8) & 0xFF)) <= threshold
			&& Math.abs((rgb1 & 0xFF) - (rgb2 & 0xFF)) <= threshold;
	}

	/**
	 * Réplique GameTestHelper.makeMockServerPlayerInLevel() et place le joueur mock
	 * 3 blocs devant le joueur local. L'armure ninja est équipée AVANT placeNewPlayer
	 * pour que le packet de spawn initial inclue déjà l'équipement.
	 */
	private static void spawnMockPlayerInFront(
		TestSingleplayerContext world,
		ClientGameTestContext context,
		String name,
		boolean equipNinjaArmor
	) {
		context.waitFor(mc -> mc.player != null && mc.player.getY() != 0);

		double[] localPos = new double[3];
		context.runOnClient(mc -> {
			localPos[0] = mc.player.getX();
			localPos[1] = mc.player.getY();
			localPos[2] = mc.player.getZ();
			// Orientation déterministe : regarder vers le sud (+Z) droit devant
			mc.player.setYRot(0.0f);
			mc.player.setXRot(0.0f);
		});

		world.getServer().runOnServer(server -> {
			ServerLevel level = server.getLevel(Level.OVERWORLD);

			// Monde déterministe : midi, temps figé, pas de météo
			Holder<WorldClock> overworldClock = server.registryAccess()
				.lookupOrThrow(Registries.WORLD_CLOCK)
				.getOrThrow(WorldClocks.OVERWORLD);
			level.clockManager().moveToTimeMarker(overworldClock, ClockTimeMarkers.NOON);
			server.getGameRules().set(GameRules.ADVANCE_TIME, false, server);

			level.getWeatherData().setRaining(false);
			level.getWeatherData().setThundering(false);
			level.getWeatherData().setClearWeatherTime(Integer.MAX_VALUE);
			server.getGameRules().set(GameRules.ADVANCE_WEATHER, false, server);

			GameProfile profile = new GameProfile(UUID.randomUUID(), name);
			CommonListenerCookie cookie = CommonListenerCookie.createInitial(profile, false);
			ServerPlayer mock = new ServerPlayer(server, level, cookie.gameProfile(), cookie.clientInformation()) {
				@Override
				public GameType gameMode() { return GameType.CREATIVE; }
			};
			if (equipNinjaArmor) {
				mock.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.NINJA_HELMET));
				mock.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.NINJA_CHESTPLATE));
				mock.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.NINJA_LEGGINGS));
				mock.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.NINJA_BOOTS));
			}
			Connection connection = new Connection(PacketFlow.SERVERBOUND);
			new EmbeddedChannel(connection);
			server.getPlayerList().placeNewPlayer(connection, mock, cookie);
			mock.setPos(localPos[0], localPos[1], localPos[2] + 3.0);
			// Orienté vers le nord (-Z), face au joueur local
			mock.setYRot(180.0f);
			mock.setXRot(0.0f);
		});

		context.waitTicks(20);
	}
}
