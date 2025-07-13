package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.mcreator.terramity.TerramityMod;
import net.mcreator.terramity.init.TerramityModParticleTypes;
import net.mcreator.terramity.procedures.MurasamaDamageProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = MurasamaDamageProcedure.class, remap = false)
public class MurasamaDamageMixin {

    /**
     * @author SwimmingTuna
     * @reason Increase Murasama damage by 4x
     */
    @Overwrite
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            TerramityMod.queueServerWork(Mth.nextInt(RandomSource.create(), 20, 32), () -> {
                Vec3 _center = new Vec3(x, y, z);
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(0.625), (e) -> {
                    return true;
                }).stream().sorted(Comparator.comparingDouble((_entcnd) -> {
                    return _entcnd.distanceToSqr(_center);
                })).toList();

                for (Entity entityiterator : _entfound) {
                    if (entityiterator != entity) {
                        entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), entity), 120.0F);
                    }
                }

                Level _level;
                if (Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
                    if (!world.isClientSide() && world instanceof Level) {
                        _level = (Level)world;
                        if (!_level.isClientSide()) {
                            _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash1")), SoundSource.PLAYERS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.1));
                        } else {
                            _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash1")), SoundSource.PLAYERS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.1), false);
                        }
                    }
                } else if (Mth.nextInt(RandomSource.create(), 1, 3) == 2) {
                    if (!world.isClientSide() && world instanceof Level) {
                        _level = (Level)world;
                        if (!_level.isClientSide()) {
                            _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash2")), SoundSource.PLAYERS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.1));
                        } else {
                            _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash2")), SoundSource.PLAYERS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.1), false);
                        }
                    }
                } else if (!world.isClientSide() && world instanceof Level) {
                    _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash3")), SoundSource.PLAYERS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.1));
                    } else {
                        _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash3")), SoundSource.PLAYERS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.1), false);
                    }
                }

                if (world instanceof ServerLevel _levelx) {
                    _levelx.sendParticles(TerramityModParticleTypes.SHINY_GLINT.get(), x, y, z, Mth.nextInt(RandomSource.create(), 8, 10), 0.02, 0.02, 0.02, 0.2);
                }
            });
        }
    }
}