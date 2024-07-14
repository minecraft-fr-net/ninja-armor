package com.cprodhomme.mixin.client;

// import com.example.ninja_mod.NinjaMod;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cprodhomme.NinjaArmorMod;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
  @Inject(at = @At("HEAD"), method = "renderLabelIfPresent(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V", cancellable = true)
  public void renderLabelIfPresent(AbstractClientPlayerEntity player, Text text, MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers, int light, float distance, CallbackInfo ci) {
    NinjaArmorMod.LOGGER.info("Checking if player is wearing ninja armor: ");
    if (isWearingNinjaArmor(player)) {
      NinjaArmorMod.LOGGER.info("c'est bon !");
      ci.cancel();
    }
  }

  private boolean isWearingNinjaArmor(AbstractClientPlayerEntity player) {
    // Vérifie si le joueur porte l'armure de ninja
    // Remplace cette logique par la vérification réelle de ton armure custom
    return player.getInventory().armor.get(0).getItem().getTranslationKey().contains("ninja") &&
            player.getInventory().armor.get(1).getItem().getTranslationKey().contains("ninja") &&
            player.getInventory().armor.get(2).getItem().getTranslationKey().contains("ninja") &&
            player.getInventory().armor.get(3).getItem().getTranslationKey().contains("ninja");
  }
}
