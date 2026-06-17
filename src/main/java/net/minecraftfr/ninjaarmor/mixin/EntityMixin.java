package net.minecraftfr.ninjaarmor.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftfr.ninjaarmor.item.ModItems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
  @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
  private void ninjaarmor$onPlayStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
    if (!((Object) this instanceof Player player)) {
      return;
    }
    if (isWearingFullNinjaArmor(player)) {
      ci.cancel();
    }
  }

  @Inject(
    method = "gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;)V",
    at = @At("HEAD"),
    cancellable = true
  )
  private void ninjaarmor$silentStepGameEvent(Holder<GameEvent> event, Entity entity, CallbackInfo ci) {
    if (!((Object) this instanceof Player player)) {
      return;
    }
    if (event == GameEvent.STEP && isWearingFullNinjaArmor(player)) {
      ci.cancel();
    }
  }

  private static boolean isWearingFullNinjaArmor(Player player) {
    return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.NINJA_HELMET
      && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.NINJA_CHESTPLATE
      && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.NINJA_LEGGINGS
      && player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.NINJA_BOOTS;
  }
}
