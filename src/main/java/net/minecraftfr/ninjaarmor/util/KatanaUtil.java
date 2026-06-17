package net.minecraftfr.ninjaarmor.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftfr.ninjaarmor.item.ModItems;
import net.minecraftfr.ninjaarmor.util.helpers.AttackHelper;

public class KatanaUtil {
  public static boolean canBreakBambooWithCriticalHit(Player player, ItemStack heldItem, Block block) {
    if (isKatana(heldItem)) {
      if (block == Blocks.BAMBOO) {
        if (AttackHelper.isCriticalHit(player)) {
          damageKatana(heldItem, player);
          return true;
        }
      }
    }
    return false;
  }

  private static boolean isKatana(ItemStack stack) {
    return stack.getItem() == ModItems.GOLDEN_KATANA ||
            stack.getItem() == ModItems.IRON_KATANA ||
            stack.getItem() == ModItems.DIAMOND_KATANA ||
            stack.getItem() == ModItems.NETHERITE_KATANA;
  }

  private static void damageKatana(ItemStack katana, Player player) {
    katana.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
  }
}
