package bigsir.soundtweaks.interfaces;

public interface ISoundOrigin {
	float getX(float partialTick);
	float getY(float partialTick);
	float getZ(float partialTick);
	default boolean isRemoved() {
		return false;
	}
}
