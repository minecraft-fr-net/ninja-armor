package net.minecraftfr.ninjaarmor.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraftfr.ninjaarmor.item.ModItems;
import net.minecraftfr.ninjaarmor.util.helpers.AttackHelper;

// Katanas in gold, iron, diamond, and Netherite can 
// instantly break bamboo with a critical hit
public class KatanaCriticalHit {
  public static void register() {
    AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
      ItemStack heldItem = player.getStackInHand(hand);

      if (isKatana(heldItem)) {
        if (world.getBlockState(pos).getBlock() == Blocks.BAMBOO) {
          if (AttackHelper.isCriticalHit(player)) {
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
}
