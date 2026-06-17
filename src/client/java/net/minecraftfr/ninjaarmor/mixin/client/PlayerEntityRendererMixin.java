package net.minecraftfr.ninjaarmor.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraftfr.ninjaarmor.item.ModItems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class PlayerEntityRendererMixin {
	@Inject(method = "submitNameDisplay", at = @At("HEAD"), cancellable = true)
	private void ninjaarmor$hideLabelWhenFullSet(
		AvatarRenderState state,
		PoseStack matrices,
		SubmitNodeCollector submitNodeCollector,
		CameraRenderState cameraRenderState,
		CallbackInfo ci
	) {
		if (state.headEquipment.getItem() == ModItems.NINJA_HELMET
			&& state.chestEquipment.getItem() == ModItems.NINJA_CHESTPLATE
			&& state.legsEquipment.getItem() == ModItems.NINJA_LEGGINGS
			&& state.feetEquipment.getItem() == ModItems.NINJA_BOOTS) {
			ci.cancel();
		}
	}
}
