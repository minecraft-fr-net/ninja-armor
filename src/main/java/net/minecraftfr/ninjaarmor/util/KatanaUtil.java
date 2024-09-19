package net.minecraftfr.ninjaarmor.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftfr.ninjaarmor.item.ModItems;
import net.minecraftfr.ninjaarmor.util.helpers.AttackHelper;

public class KatanaUtil {
  /**
   * Vérifie si un joueur peut détruire un bambou en un seul coup avec un katana.
   *
   * @param player Le joueur qui attaque.
   * @param heldItem L'arme dans la main du joueur.
   * @param block Le bloc attaqué.
   * @return true si le joueur peut détruire le bambou en un coup critique.
   */
  public static boolean canBreakBambooWithCriticalHit(PlayerEntity player, ItemStack heldItem, Block block) {
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

  // Vérifie si l'item est un katana en or, fer, diamant ou Netherite
  private static boolean isKatana(ItemStack stack) {
    return stack.getItem() == ModItems.GOLDEN_KATANA ||
            stack.getItem() == ModItems.IRON_KATANA ||
            stack.getItem() == ModItems.DIAMOND_KATANA ||
            stack.getItem() == ModItems.NETHERITE_KATANA;
  }

  private static void damageKatana(ItemStack katana, PlayerEntity player) {
    katana.damage(1, player, EquipmentSlot.MAINHAND);
  }
}
