package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.SoundHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
	@Shadow
	@Final
	private Timer timer;

	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundEngine;updateListener(Lnet/minecraft/core/entity/Mob;F)V", shift = At.Shift.AFTER))
	public void updateSources(CallbackInfo ci) {
		SoundHelper.updateSources(this.timer.partialTicks);
	}
}
