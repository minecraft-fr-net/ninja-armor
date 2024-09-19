package net.minecraftfr.ninjaarmor.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraftfr.ninjaarmor.util.KatanaUtil;

public class KatanaCriticalHitEvent {
  public static void register() {
    AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
      if (player.isSpectator()) {
        return ActionResult.PASS;
      }
      if (player.isCreative()) {
        world.breakBlock(pos, false);
        return ActionResult.PASS;
      }

      ItemStack heldItem = player.getStackInHand(hand);
      Block block = world.getBlockState(pos).getBlock();

      if (KatanaUtil.canBreakBambooWithCriticalHit(player, heldItem, block)) {
        world.breakBlock(pos, true);
        return ActionResult.SUCCESS;
      }
      return ActionResult.PASS;
    });
  }
}
