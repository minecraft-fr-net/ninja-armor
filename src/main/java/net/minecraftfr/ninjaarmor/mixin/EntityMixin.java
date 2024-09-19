package net.minecraftfr.ninjaarmor.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraftfr.ninjaarmor.item.ModItems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
  @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
  private void onPlayStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
    LivingEntity entity = (LivingEntity) (Object) this;

    // Vérifier si l'entité est un joueur et si elle porte les bottes Ninja
    if (entity instanceof PlayerEntity player) {
      if (isWearingNinjaBoots(player)) {
        // Modifier le volume du son de pas, sans ajouter un nouveau son
        BlockSoundGroup blockSoundGroup = state.getSoundGroup();
        reduceStepSoundVolume(player, blockSoundGroup);
        ci.cancel(); // Annule le son par défaut
      }
    }
  }

  // check if player is wearing ninja boots
  private boolean isWearingNinjaBoots(PlayerEntity player) {
    return player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.NINJA_BOOTS;
  }

  // reduce step by 2
  private void reduceStepSoundVolume(PlayerEntity player, BlockSoundGroup blockSoundGroup) {
    float volume = (blockSoundGroup.getVolume() * 0.15F) / 2;
    player.playSound(blockSoundGroup.getStepSound(), volume, blockSoundGroup.getPitch());
  }
}
