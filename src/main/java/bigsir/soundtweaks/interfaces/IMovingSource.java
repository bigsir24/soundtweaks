package bigsir.soundtweaks.interfaces;

public interface IMovingSource {
	void updatePosition(float partialTick);
	void setOrigin(ISoundOrigin origin);
}
