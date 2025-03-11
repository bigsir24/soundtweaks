package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.SoundHelper;
import bigsir.soundtweaks.interfaces.IMovingSource;
import bigsir.soundtweaks.interfaces.ISoundOrigin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import paulscode.sound.Source;

@Mixin(value = Source.class, remap = false)
public abstract class SourceMixin implements IMovingSource {
	@Shadow
	public abstract void setPosition(float v, float v1, float v2);

	@Shadow
	public abstract void cull();

	@Unique
	private ISoundOrigin origin;

	@Override
	public void updatePosition(float partialTick) {
		if (origin.isRemoved()) {
			this.cull();
		}
		this.setPosition(origin.getX(partialTick), origin.getY(partialTick), origin.getZ(partialTick));
	}

	@Override
	public void setOrigin(ISoundOrigin origin) {
		if(origin != null) {
			SoundHelper.setToUpdate(this);
			this.origin = origin;
		}
	}
}
