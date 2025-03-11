package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.SoundHelper;
import bigsir.soundtweaks.interfaces.ISoundOrigin;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = World.class, remap = false)
public abstract class WorldMixin {
	@Inject(method = "playSoundAtEntity", at = @At(value = "INVOKE" ,target = "Lnet/minecraft/core/world/LevelListener;playSound(Lnet/minecraft/core/entity/Entity;Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;DDDFF)V"))
	public void attach(Entity player, Entity entity, String soundPath, float volume, float pitch, CallbackInfo ci) {
		SoundHelper.lock();
		if (entity instanceof ISoundOrigin) SoundHelper.setOrigin((ISoundOrigin) entity);
	}

	@Inject(method = "playSoundAtEntity", at = @At(value = "INVOKE" ,target = "Lnet/minecraft/core/world/LevelListener;playSound(Lnet/minecraft/core/entity/Entity;Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;DDDFF)V", shift = At.Shift.AFTER))
	public void detach(Entity player, Entity entity, String soundPath, float volume, float pitch, CallbackInfo ci) {
		SoundHelper.setOrigin(null);
		SoundHelper.unlock();
	}
}
