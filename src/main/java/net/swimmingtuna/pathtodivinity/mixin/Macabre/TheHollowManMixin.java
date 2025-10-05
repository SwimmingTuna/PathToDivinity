package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.entity.TheHollowManEntity;
import com.curseforge.macabre.procedures.TheHollowManEntityIsHurtProcedure;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TheHollowManEntity.class)
public class TheHollowManMixin {
    private static final Logger LOGGER = LogManager.getLogger("LOTM");

    @Inject(method = "hurt", at = @At("HEAD"))
    private void onHurtStart(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        TheHollowManEntity entity = (TheHollowManEntity)(Object)this;

        LOGGER.info("========== HOLLOW MAN HURT DEBUG ==========");
        LOGGER.info("Damage Amount: {}", amount);
        LOGGER.info("Damage Source: {}", source.getMsgId());
        LOGGER.info("Damage Type: {}", source.type());
        LOGGER.info("Current Health: {}/{}", entity.getHealth(), entity.getMaxHealth());
        LOGGER.info("Attacker: {}", source.getEntity() != null ? source.getEntity().getName().getString() : "null");
        LOGGER.info("Direct Entity: {}", source.getDirectEntity() != null ? source.getDirectEntity().getName().getString() : "null");
        LOGGER.info("Is Invulnerable: {}", entity.isInvulnerable());
        LOGGER.info("Invulnerable Time: {}", entity.invulnerableTime);

        // Check for existing resistance
        if (entity instanceof LivingEntity) {
            MobEffectInstance resistance = ((LivingEntity)entity).getEffect(MobEffects.DAMAGE_RESISTANCE);
            if (resistance != null) {
                LOGGER.info("EXISTING RESISTANCE: Level {} for {} ticks",
                        resistance.getAmplifier(), resistance.getDuration());
            } else {
                LOGGER.info("No existing resistance effect");
            }
        }

        // Log stack trace to see what called this
        LOGGER.info("Called from:");
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < Math.min(8, trace.length); i++) {
            LOGGER.info("  {}", trace[i]);
        }
    }

    @Inject(method = "hurt", at = @At("RETURN"))
    private void onHurtEnd(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        TheHollowManEntity entity = (TheHollowManEntity)(Object)this;

        LOGGER.info("Damage Applied: {}", cir.getReturnValue());
        LOGGER.info("Health After: {}/{}", entity.getHealth(), entity.getMaxHealth());

        // Check for resistance after procedure runs
        if (entity instanceof LivingEntity) {
            MobEffectInstance resistance = ((LivingEntity)entity).getEffect(MobEffects.DAMAGE_RESISTANCE);
            if (resistance != null) {
                LOGGER.info("RESISTANCE AFTER PROCEDURE: Level {} for {} ticks",
                        resistance.getAmplifier(), resistance.getDuration());
                LOGGER.info("WARNING: Boss has Resistance {} - this blocks {}% of damage!",
                        resistance.getAmplifier() + 1,
                        Math.min(100, (resistance.getAmplifier() + 1) * 20));
            }
        }
        LOGGER.info("==========================================");
    }
}