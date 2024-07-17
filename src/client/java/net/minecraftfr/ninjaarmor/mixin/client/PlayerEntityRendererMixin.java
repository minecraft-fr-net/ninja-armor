package net.minecraftfr.ninjaarmor.mixin.client;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import net.minecraftfr.ninjaarmor.ModItems;
import net.minecraftfr.ninjaarmor.NinjaArmor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
  @Inject(at = @At("HEAD"), method = "renderLabelIfPresent(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V", cancellable = true)
  public void renderLabelIfPresent(AbstractClientPlayerEntity player, Text text, MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers, int light, float distance, CallbackInfo ci) {
    NinjaArmor.LOGGER.info("Checking if player is wearing ninja armor: ");
    if (isWearingNinjaArmor(player)) {
      NinjaArmor.LOGGER.info("c'est bon !");
      ci.cancel();
    }
  }

  // Vérifie si le joueur porte l'armure de ninja
  private boolean isWearingNinjaArmor(AbstractClientPlayerEntity player) {
    return player.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.NINJA_HELMET &&
           player.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.NINJA_CHESTPLATE &&
           player.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.NINJA_LEGGINGS &&
           player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.NINJA_BOOTS;
  }
}
