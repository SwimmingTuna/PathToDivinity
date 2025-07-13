package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.MacabreMod;
import com.curseforge.macabre.procedures.GrossbowProjProjectileHitsLivingEntityProcedure;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.swimmingtuna.lotm.util.BeyonderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = GrossbowProjProjectileHitsLivingEntityProcedure.class, remap = false)
public class GrossbowProjectileHitsMixin {

    /**
     * @author YourName
     * @reason Replace explosion with BeyonderUtil.destroyBlocksInSphere
     */
    @Overwrite
    public static void execute(LevelAccessor level, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.level().isClientSide()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 180, 2, false, false));
                }
            }

            MacabreMod.queueServerWork(1, () -> {
                if (!level.isClientSide()) {
                    BeyonderUtil.destroyBlocksInSphere(entity, entity.getOnPos(), 8, 60);
                }
            });
        }
    }
}