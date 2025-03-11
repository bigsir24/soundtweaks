package bigsir.soundtweaks;

import bigsir.soundtweaks.interfaces.IMovingSource;
import bigsir.soundtweaks.interfaces.IPassOrigin;
import bigsir.soundtweaks.interfaces.ISoundEngine;
import bigsir.soundtweaks.interfaces.ISoundOrigin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SoundHelper {
	private static SoundEngine engine;
	private static final List<IMovingSource> movingSource = new ArrayList<>();

	public static void init(Minecraft minecraft) {
		engine = minecraft.sndManager;
	}

	public static void updateSources(float partialTick) {
		for (IMovingSource source : movingSource) {
			source.updatePosition(partialTick);
		}
	}

	public static void lock(){
		((ISoundEngine)engine).getLock().lock();
	}

	public static void setOrigin(ISoundOrigin origin) {
		((IPassOrigin)engine).setOrigin(origin);
	}

	public static void unlock(){
		((ISoundEngine)engine).getLock().unlock();
	}

	public static void setToUpdate(IMovingSource source) {
		movingSource.add(source);
	}

	public static void playMovingSoundAt(@NotNull World world, @Nullable Entity player, @NotNull Entity entity, String soundPath, float volume, float pitch) {
	}
}
