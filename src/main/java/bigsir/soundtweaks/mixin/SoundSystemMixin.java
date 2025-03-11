package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.interfaces.IPassOrigin;
import bigsir.soundtweaks.interfaces.ISoundOrigin;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulscode.sound.CommandObject;
import paulscode.sound.FilenameURL;
import paulscode.sound.Library;
import paulscode.sound.SoundSystem;

@Mixin(value = SoundSystem.class, remap = false)
public abstract class SoundSystemMixin implements IPassOrigin {
	@Shadow
	protected Library soundLibrary;

	@Unique
	private ISoundOrigin origin;

	@Override
	public void setOrigin(ISoundOrigin origin) {
		this.origin = origin;
	}

	@Redirect(method = "newSource(ZLjava/lang/String;Ljava/net/URL;Ljava/lang/String;ZFFFIF)V", at = @At(value = "NEW", target = "(IZZZLjava/lang/String;Ljava/lang/Object;FFFIF)Lpaulscode/sound/CommandObject;"))
	public CommandObject attachObject(int i, boolean b1, boolean b2, boolean b3, String string, Object object, float x, float y, float z, int a, float b) {
		CommandObject obj = new CommandObject(i, b1, b2, b3, string, object, x, y, z, a, b);
		((IPassOrigin)obj).setOrigin(origin);
		return obj;
	}

	@Redirect(method = "newSource(ZLjava/lang/String;Ljava/lang/String;ZFFFIF)V", at = @At(value = "NEW", target = "(IZZZLjava/lang/String;Ljava/lang/Object;FFFIF)Lpaulscode/sound/CommandObject;"))
	public CommandObject attachObject2(int i, boolean b1, boolean b2, boolean b3, String string, Object object, float x, float y, float z, int a, float b) {
		CommandObject obj = new CommandObject(i, b1, b2, b3, string, object, x, y, z, a, b);
		((IPassOrigin)obj).setOrigin(origin);
		return obj;
	}

	@Inject(method = "CommandNewSource", at = @At(value = "INVOKE", target = "Lpaulscode/sound/Library;newSource(ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;FFFIF)V", shift = At.Shift.BEFORE))
	public void addOrigin(boolean par1, boolean par2, boolean par3, String par4, FilenameURL par5, float par6, float par7, float par8, int par9, float par10, CallbackInfo ci) {
		((IPassOrigin)soundLibrary).setOrigin(origin);
	}

	@Inject(method = "CommandQueue", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;CommandNewSource(ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;FFFIF)V", shift = At.Shift.BEFORE))
	public void attach(CommandObject par1, CallbackInfoReturnable<Boolean> cir, @Local(name = "var4") CommandObject object) {
		this.origin = ((IPassOrigin)object).getOrigin();
	}

	@Inject(method = "CommandQueue", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;CommandNewSource(ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;FFFIF)V", shift = At.Shift.AFTER))
	public void detach(CommandObject par1, CallbackInfoReturnable<Boolean> cir, @Local(name = "var4") CommandObject object) {
		this.origin = null;
	}
}
