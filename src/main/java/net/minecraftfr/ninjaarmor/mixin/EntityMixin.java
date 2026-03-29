package net.minecraftfr.ninjaarmor.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import net.minecraftfr.ninjaarmor.item.ModItems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
  @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
  private void ninjaarmor$onPlayStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
    if (!((Object) this instanceof PlayerEntity player)) {
      return;
    }
    if (isWearingFullNinjaArmor(player)) {
      ci.cancel();
    }
  }

  @Inject(
    method = "emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/entity/Entity;)V",
    at = @At("HEAD"),
    cancellable = true
  )
  private void ninjaarmor$silentStepGameEvent(RegistryEntry<GameEvent> event, Entity entity, CallbackInfo ci) {
    if (!((Object) this instanceof PlayerEntity player)) {
      return;
    }
    if (event == GameEvent.STEP && isWearingFullNinjaArmor(player)) {
      ci.cancel();
    }
  }

  private static boolean isWearingFullNinjaArmor(PlayerEntity player) {
    return player.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.NINJA_HELMET
      && player.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.NINJA_CHESTPLATE
      && player.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.NINJA_LEGGINGS
      && player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.NINJA_BOOTS;
  }
}
