package bigsir.soundtweaks;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class SoundTweaks implements ModInitializer, ClientStartEntrypoint {
    public static final String MOD_ID = "soundtweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("Sound Tweaks initialized.");
    }

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		SoundHelper.init(Minecraft.getMinecraft());
	}
}
