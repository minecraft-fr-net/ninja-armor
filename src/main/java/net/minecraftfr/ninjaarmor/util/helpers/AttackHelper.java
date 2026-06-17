package net.minecraftfr.ninjaarmor.util.helpers;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class AttackHelper {
  public static boolean isCriticalHit(Player player) {
    return player.fallDistance > 0.0F
      && !player.onGround()
      && !player.onClimbable()
      && !player.isInWater()
      && !player.hasEffect(MobEffects.BLINDNESS)
      && !player.isPassenger();
  }
}
