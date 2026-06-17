package net.minecraftfr.ninjaarmor.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftfr.ninjaarmor.item.ModItems;

/**
 * Vérifie le silence des pas (événement de jeu {@link GameEvent#STEP}) pour le capteur Sculk
 * lorsque le joueur porte l'armure ninja complète, et la détection normale sans cette armure.
 */
public final class NinjaArmorGameTestSuite {
	/** Capteur sur la dalle de pierre (2,0,2). */
	private static final BlockPos SENSOR_REL = new BlockPos(2, 1, 2);
	/**
	 * Pieds du joueur à côté du capteur (distance horizontale ~1 bloc) pour rester dans le rayon de
	 * notification de {@link GameEvent#STEP}.
	 */
	private static final Vec3 PLAYER_FEET_REL = new Vec3(3.5, 1.0, 2.5);

	@GameTest(structure = "fabric-gametest-api-v1:empty", maxTicks = 80, setupTicks = 1)
	public void fullNinjaArmorSuppressesStepGameEventForSculk(GameTestHelper context) {
		placeArena(context);
		ServerPlayer player = context.makeMockServerPlayerInLevel();
		equipFullNinja(player);
		teleportPlayerNearSensor(context, player);

		context.runAtTickTime(2L, () -> {
			refreshPlayerFeet(context, player);
			player.gameEvent(GameEvent.STEP, player);
		});
		context.runAtTickTime(3L, () -> {
			var state = context.getBlockState(SENSOR_REL);
			context.assertTrue(
				SculkSensorBlock.getPhase(state) == SculkSensorPhase.INACTIVE,
				"Le capteur Sculk doit rester inactif : l'événement STEP ne doit pas être émis avec l'armure ninja complète."
			);
			context.succeed();
		});
	}

	@GameTest(structure = "fabric-gametest-api-v1:empty", maxTicks = 80, setupTicks = 1)
	public void barePlayerStepGameEventTriggersSculk(GameTestHelper context) {
		placeArena(context);
		ServerPlayer player = context.makeMockServerPlayerInLevel();
		clearArmor(player);
		teleportPlayerNearSensor(context, player);

		context.runAtTickTime(2L, () -> {
			refreshPlayerFeet(context, player);
			player.gameEvent(GameEvent.STEP, player);
		});
		context.runAtTickTime(3L, () -> {
			var state = context.getBlockState(SENSOR_REL);
			context.assertFalse(
				SculkSensorBlock.getPhase(state) == SculkSensorPhase.INACTIVE,
				"Le capteur Sculk doit réagir à l'événement STEP lorsque le joueur ne porte pas l'armure ninja complète."
			);
			context.succeed();
		});
	}

	private static void placeArena(GameTestHelper context) {
		for (int x = 0; x < 8; x++) {
			for (int z = 0; z < 8; z++) {
				context.setBlock(new BlockPos(x, 0, z), Blocks.STONE.defaultBlockState());
			}
		}
		context.setBlock(SENSOR_REL, Blocks.SCULK_SENSOR.defaultBlockState());
	}

	private static void teleportPlayerNearSensor(GameTestHelper context, ServerPlayer player) {
		refreshPlayerFeet(context, player);
	}

	private static void refreshPlayerFeet(GameTestHelper context, ServerPlayer player) {
		Vec3 pos = context.absoluteVec(PLAYER_FEET_REL);
		player.setPos(pos.x, pos.y, pos.z);
	}

	private static void equipFullNinja(ServerPlayer player) {
		player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.NINJA_HELMET));
		player.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.NINJA_CHESTPLATE));
		player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.NINJA_LEGGINGS));
		player.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.NINJA_BOOTS));
	}

	private static void clearArmor(ServerPlayer player) {
		player.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
		player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
		player.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
		player.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
	}
}
