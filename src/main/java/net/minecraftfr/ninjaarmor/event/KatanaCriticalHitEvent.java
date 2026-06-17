package net.minecraftfr.ninjaarmor.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftfr.ninjaarmor.util.KatanaUtil;

public class KatanaCriticalHitEvent {
  public static void register() {
    AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
      if (player.isSpectator()) {
        return InteractionResult.PASS;
      }
      if (player.isCreative()) {
        world.destroyBlock(pos, false, player, 512);
        return InteractionResult.PASS;
      }

      ItemStack heldItem = player.getItemInHand(hand);
      Block block = world.getBlockState(pos).getBlock();

      if (KatanaUtil.canBreakBambooWithCriticalHit(player, heldItem, block)) {
        world.destroyBlock(pos, true, player, 512);
        return InteractionResult.SUCCESS;
      }
      return InteractionResult.PASS;
    });
  }
}
