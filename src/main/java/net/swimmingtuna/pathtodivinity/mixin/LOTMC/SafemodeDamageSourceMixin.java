package net.swimmingtuna.pathtodivinity.mixin.LOTMC;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.swimmingtuna.lotm.util.BeyonderUtil;
import net.swimmingtuna.pathtodivinity.profile.AbilityDamageTracker;
import net.swimmingtuna.pathtodivinity.profile.ProfileManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Flags Beyonder ability damage that originates from a Safemode player.
 *
 * <p>{@code BeyonderUtil}'s {@code *Source} factories are the one seam that sees both the attacker and the
 * damage source, which makes them the right place to decide "this is an ability, and a Safemode player cast
 * it". The reduction itself happens later in {@code ModEvents.hurtEvent}, where the victim is known — the rule
 * only applies against other players.
 *
 * <p>Only Safemode sources are recorded, so the tracking set stays near-empty on a normal server.
 */
@Mixin(value = BeyonderUtil.class, remap = false)
public class SafemodeDamageSourceMixin {

    /**
     * The ten factories that share an {@code (attacker, target)} signature. LOTM passes the attacker first and
     * uses it for both the source's causing and direct entity, so {@code DamageSource.getEntity()} resolves
     * back to this player later on.
     */
    @Inject(
            method = {
                    "genericSource", "magicSource", "lightningSource", "explosionSource", "fallSource",
                    "freezeSource", "lavaSource", "fallingBlockSource", "crushSource", "enslavementSource"
            },
            at = @At("RETURN"),
            remap = false
    )
    private static void pathtodivinity$markSafemodeAbilitySource(Entity attacker, Entity target,
                                                                 CallbackInfoReturnable<DamageSource> cir) {
        markIfSafemode(attacker, cir.getReturnValue());
    }

    /** Mental damage takes the level first, so its attacker sits at index 1. */
    @Inject(method = "mentalSource", at = @At("RETURN"), remap = false)
    private static void pathtodivinity$markSafemodeMentalSource(Level level, LivingEntity attacker,
                                                                LivingEntity target,
                                                                CallbackInfoReturnable<DamageSource> cir) {
        markIfSafemode(attacker, cir.getReturnValue());
    }

    private static void markIfSafemode(Entity attacker, DamageSource source) {
        // getServer() is null on the client, so isSafemode returns false there and nothing is tracked.
        if (attacker instanceof Player player && source != null && ProfileManager.isSafemode(player)) {
            AbilityDamageTracker.markSafemodeAbilityDamage(source);
        }
    }
}
