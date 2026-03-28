package net.minecraftfr.ninjaarmor.mixin.client;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftfr.ninjaarmor.item.ModItems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
	@Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
	private void ninjaarmor$hideLabelWhenFullSet(
		PlayerEntityRenderState state,
		MatrixStack matrices,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState,
		CallbackInfo ci
	) {
		if (state.equippedHeadStack.getItem() == ModItems.NINJA_HELMET
			&& state.equippedChestStack.getItem() == ModItems.NINJA_CHESTPLATE
			&& state.equippedLegsStack.getItem() == ModItems.NINJA_LEGGINGS
			&& state.equippedFeetStack.getItem() == ModItems.NINJA_BOOTS) {
			ci.cancel();
		}
	}
}
