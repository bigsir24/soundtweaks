package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.interfaces.IPassOrigin;
import bigsir.soundtweaks.interfaces.ISoundEngine;
import bigsir.soundtweaks.interfaces.ISoundOrigin;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.core.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

import java.util.concurrent.locks.Lock;

@Mixin(value = SoundEngine.class, remap = false)
public abstract class SoundEngineMixin implements ISoundEngine, IPassOrigin {

	@Shadow
	private static Lock lock;

	@Shadow
	private static @Nullable SoundSystem soundSystem;

	@Unique
	private ISoundOrigin origin;

	@Override
	public Lock getLock() {
		return lock;
	}

	@Override
	public void setOrigin(ISoundOrigin origin) {
		this.origin = origin;
	}

	@Inject(method = "playSoundWithIdAtPos(Lnet/minecraft/client/sound/SoundEntry;Lnet/minecraft/core/sound/SoundCategory;FFFFFLjava/lang/String;Z)V", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;newSource(ZLjava/lang/String;Ljava/net/URL;Ljava/lang/String;ZFFFIF)V", shift = At.Shift.BEFORE))
	public void attach(SoundEntry entry, SoundCategory category, float x, float y, float z, float volume, float pitch, String soundID, boolean allowSubtitle, CallbackInfo ci) {
		((IPassOrigin)soundSystem).setOrigin(origin);
	}

	@Inject(method = "playSoundWithIdAtPos(Lnet/minecraft/client/sound/SoundEntry;Lnet/minecraft/core/sound/SoundCategory;FFFFFLjava/lang/String;Z)V", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;newSource(ZLjava/lang/String;Ljava/net/URL;Ljava/lang/String;ZFFFIF)V", shift = At.Shift.AFTER))
	public void detach(SoundEntry entry, SoundCategory category, float x, float y, float z, float volume, float pitch, String soundID, boolean allowSubtitle, CallbackInfo ci) {
		((IPassOrigin)soundSystem).setOrigin(null);
	}

	@ModifyConstant(method = "playSoundAt(Lnet/minecraft/client/sound/SoundEntry;Lnet/minecraft/core/sound/SoundCategory;FFFFF)V", constant = @Constant(intValue = 256))
	private int mod(int constant) {
		return constant;
	}
}
