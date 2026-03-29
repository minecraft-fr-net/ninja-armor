package net.minecraftfr.ninjaarmor.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraftfr.ninjaarmor.item.ModItems;

/**
 * Vérifie le silence des pas (événement de jeu {@link GameEvent#STEP}) pour le capteur Sculk
 * lorsque le joueur porte l'armure ninja complète, et la détection normale sans cette armure.
 */
@SuppressWarnings("removal")
public final class NinjaArmorGameTestSuite {
	/** Capteur sur la dalle de pierre (2,0,2). */
	private static final BlockPos SENSOR_REL = new BlockPos(2, 1, 2);
	/**
	 * Pieds du joueur à côté du capteur (distance horizontale ~1 bloc) pour rester dans le rayon de
	 * notification de {@link GameEvent#STEP}.
	 */
	private static final Vec3d PLAYER_FEET_REL = new Vec3d(3.5, 1.0, 2.5);

	@GameTest(structure = "fabric-gametest-api-v1:empty", maxTicks = 80, setupTicks = 1)
	public void fullNinjaArmorSuppressesStepGameEventForSculk(TestContext context) {
		placeArena(context);
		ServerPlayerEntity player = context.createMockCreativeServerPlayerInWorld();
		equipFullNinja(player);
		teleportPlayerNearSensor(context, player);

		context.runAtTick(2L, () -> {
			refreshPlayerFeet(context, player);
			player.emitGameEvent(GameEvent.STEP, player);
		});
		context.runAtTick(3L, () -> {
			var state = context.getBlockState(SENSOR_REL);
			context.assertTrue(
				SculkSensorBlock.isInactive(state),
				Text.literal("Le capteur Sculk doit rester inactif : l'événement STEP ne doit pas être émis avec l'armure ninja complète.")
			);
			context.assertTrue(
				SculkSensorBlock.getPhase(state) == SculkSensorPhase.INACTIVE,
				Text.literal("Phase Sculk attendue : INACTIVE.")
			);
			context.complete();
		});
	}

	@GameTest(structure = "fabric-gametest-api-v1:empty", maxTicks = 80, setupTicks = 1)
	public void barePlayerStepGameEventTriggersSculk(TestContext context) {
		placeArena(context);
		ServerPlayerEntity player = context.createMockCreativeServerPlayerInWorld();
		clearArmor(player);
		teleportPlayerNearSensor(context, player);

		context.runAtTick(2L, () -> {
			refreshPlayerFeet(context, player);
			player.emitGameEvent(GameEvent.STEP, player);
		});
		context.runAtTick(3L, () -> {
			var state = context.getBlockState(SENSOR_REL);
			context.assertFalse(
				SculkSensorBlock.isInactive(state),
				Text.literal("Le capteur Sculk doit réagir à l'événement STEP lorsque le joueur ne porte pas l'armure ninja complète.")
			);
			context.complete();
		});
	}

	private static void placeArena(TestContext context) {
		for (int x = 0; x < 8; x++) {
			for (int z = 0; z < 8; z++) {
				context.setBlockState(new BlockPos(x, 0, z), Blocks.STONE.getDefaultState());
			}
		}
		context.setBlockState(SENSOR_REL, Blocks.SCULK_SENSOR.getDefaultState());
	}

	private static void teleportPlayerNearSensor(TestContext context, ServerPlayerEntity player) {
		refreshPlayerFeet(context, player);
	}

	private static void refreshPlayerFeet(TestContext context, ServerPlayerEntity player) {
		Vec3d pos = context.getAbsolute(PLAYER_FEET_REL);
		player.refreshPositionAndAngles(pos.x, pos.y, pos.z, 0.0F, 0.0F);
	}

	private static void equipFullNinja(ServerPlayerEntity player) {
		player.equipStack(EquipmentSlot.HEAD, new ItemStack(ModItems.NINJA_HELMET));
		player.equipStack(EquipmentSlot.CHEST, new ItemStack(ModItems.NINJA_CHESTPLATE));
		player.equipStack(EquipmentSlot.LEGS, new ItemStack(ModItems.NINJA_LEGGINGS));
		player.equipStack(EquipmentSlot.FEET, new ItemStack(ModItems.NINJA_BOOTS));
	}

	private static void clearArmor(ServerPlayerEntity player) {
		player.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
		player.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
		player.equipStack(EquipmentSlot.LEGS, ItemStack.EMPTY);
		player.equipStack(EquipmentSlot.FEET, ItemStack.EMPTY);
	}
}
