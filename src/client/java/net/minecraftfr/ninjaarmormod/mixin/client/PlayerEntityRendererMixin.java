package net.minecraftfr.ninjaarmormod.mixin.client;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraftfr.ninjaarmormod.NinjaArmorMod;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
