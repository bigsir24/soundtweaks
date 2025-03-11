package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.interfaces.IPassOrigin;
import bigsir.soundtweaks.interfaces.ISoundOrigin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import paulscode.sound.CommandObject;

@Mixin(value = CommandObject.class, remap = false)
public abstract class CommandObjectMixin implements IPassOrigin {
	@Unique
	private ISoundOrigin origin;

	@Override
	public void setOrigin(ISoundOrigin origin) {
		this.origin = origin;
	}

	@Override
	public ISoundOrigin getOrigin() {
		return origin;
	}
}
