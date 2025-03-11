package bigsir.soundtweaks.mixin.excluded;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobSkeleton;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Entity.class, remap = false)
abstract class EntityMixin {
	@Redirect(method = "checkOnWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundAtEntity(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/entity/Entity;Ljava/lang/String;FF)V"))
	public void soundFix(World world, Entity player, Entity entity, String soundPath, float volume, float pitch) {
		world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, entity.x, entity.y - (double)entity.heightOffset, entity.z, soundPath, volume, pitch);
	}
}

@Mixin(value = MobSkeleton.class, remap = false)
abstract class MobSkeletonMixin {
	@Redirect(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundAtEntity(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/entity/Entity;Ljava/lang/String;FF)V"))
	public void soundFix(World world, Entity player, Entity entity, String soundPath, float volume, float pitch) {
		world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, entity.x, entity.y - (double)entity.heightOffset, entity.z, soundPath, volume, pitch);
	}
}

@Mixin(value = Mob.class, remap = false)
abstract class MobMixin {
	@Redirect(method = "moveEntityWithHeading", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundAtEntity(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/entity/Entity;Ljava/lang/String;FF)V"))
	public void soundFix(World world, Entity player, Entity entity, String soundPath, float volume, float pitch) {
		world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, entity.x, entity.y - (double)entity.heightOffset, entity.z, soundPath, volume, pitch);
	}
}
