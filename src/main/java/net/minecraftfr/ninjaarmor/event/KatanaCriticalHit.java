package net.minecraftfr.ninjaarmor.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraftfr.ninjaarmor.item.ModItems;

// Katanas in gold, iron, diamond, and Netherite can 
// instantly break bamboo with a critical hit
public class KatanaCriticalHit {
  public static void register() {
    AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
      ItemStack heldItem = player.getStackInHand(hand);

      if (isKatana(heldItem)) {
        if (world.getBlockState(pos).getBlock() == Blocks.BAMBOO) {
          if (isCriticalHit(player)) {
            world.breakBlock(pos, true);
            return ActionResult.SUCCESS;
          }
        }
      }
      return ActionResult.PASS;
    });
  }

  private static boolean isKatana(ItemStack stack) {
    return stack.getItem() == ModItems.GOLDEN_KATANA ||
           stack.getItem() == ModItems.IRON_KATANA ||
           stack.getItem() == ModItems.DIAMOND_KATANA ||
           stack.getItem() == ModItems.NETHERITE_KATANA;
  }

  private static boolean isCriticalHit(PlayerEntity player) {
    return player.fallDistance > 0.0F
      && !player.isOnGround()
      && !player.isClimbing()
      && !player.isTouchingWater()
      && !player.hasStatusEffect(StatusEffects.BLINDNESS)
      && !player.hasVehicle();
  }
}
