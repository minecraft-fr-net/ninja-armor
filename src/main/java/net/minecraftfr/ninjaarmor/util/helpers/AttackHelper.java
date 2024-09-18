package net.minecraftfr.ninjaarmor.util.helpers;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class AttackHelper {
  public static boolean isCriticalHit(PlayerEntity player) {
    return player.fallDistance > 0.0F
      && !player.isOnGround()
      && !player.isClimbing()
      && !player.isTouchingWater()
      && !player.hasStatusEffect(StatusEffects.BLINDNESS)
      && !player.hasVehicle();
  }
}
