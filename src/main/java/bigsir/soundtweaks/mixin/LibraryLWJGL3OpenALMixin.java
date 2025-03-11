package bigsir.soundtweaks.mixin;

import bigsir.soundtweaks.interfaces.IMovingSource;
import bigsir.soundtweaks.interfaces.IPassOrigin;
import bigsir.soundtweaks.interfaces.ISoundOrigin;
import net.betterthanadventure.sound.LibraryLWJGL3OpenAL;
import net.betterthanadventure.sound.SourceLWJGL3OpenAL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import paulscode.sound.FilenameURL;
import paulscode.sound.Library;
import paulscode.sound.SoundBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@Mixin(value = LibraryLWJGL3OpenAL.class, remap = false)
public abstract class LibraryLWJGL3OpenALMixin extends Library implements IPassOrigin {
	@Unique
	private ISoundOrigin origin;

	@Redirect(method = "newSource", at = @At(value = "NEW", target = "(Ljava/nio/FloatBuffer;Ljava/nio/IntBuffer;ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;Lpaulscode/sound/SoundBuffer;FFFIFZ)Lnet/betterthanadventure/sound/SourceLWJGL3OpenAL;"))
	public SourceLWJGL3OpenAL newSource(FloatBuffer var1, IntBuffer var2, boolean var3, boolean var4, boolean var5, String var6, FilenameURL var7, SoundBuffer var8, float var9, float var10, float var11, int var12, float var13, boolean var14) {
		SourceLWJGL3OpenAL source = new SourceLWJGL3OpenAL(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);
		((IMovingSource)source).setOrigin(origin);
		this.origin = null;
		return source;
	}

	@Override
	public void setOrigin(ISoundOrigin origin) {
		this.origin = origin;
	}
}
