package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.interfaces.ISoundOrigin;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixin extends Entity implements ISoundOrigin {

	@Shadow
	protected boolean dead;

	public MobMixin(@Nullable World world) {
		super(world);
	}

	@Inject(method = "playLivingSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundAtEntity(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/entity/Entity;Ljava/lang/String;FF)V"), cancellable = true)
	public void noSound(CallbackInfo ci) {
		if(this.dead || this.removed) ci.cancel();
	}

	@Override
	public float getX(float partialTick) {
		return lerp(this.x, this.xo, partialTick);
	}

	@Override
	public float getY(float partialTick) {
		return lerp(this.y, this.yo, partialTick);
	}

	@Override
	public float getZ(float partialTick) {
		return lerp(this.z, this.zo, partialTick);
	}

	@Unique
	private float lerp(double current, double old, float partialTick) {
		return (float) (old + (current - old) * partialTick);
	}

	@Override
	public boolean isRemoved() {
		return this.removed;
	}
}
