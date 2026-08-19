package net.swimmingtuna.pathtodivinity.profile;

import net.minecraft.world.damagesource.DamageSource;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Remembers which {@link DamageSource} instances were produced by a Safemode player's Beyonder ability, so
 * {@code ModEvents.hurtEvent} can reduce them when the victim is another player.
 *
 * <p>Classifying ability damage by damage <em>type</em> does not work: LOTM's most-used factory is
 * {@code BeyonderUtil.genericSource}, which produces {@code DamageTypes.GENERIC}, so a type allowlist would
 * sweep in ordinary environmental damage. Identity is the reliable signal — LOTM builds a fresh
 * {@code DamageSource} on every call, so the instance uniquely identifies one ability use.
 *
 * <p>Entries are held weakly. These sources live only for the duration of a hurt, so the map self-drains and
 * nothing needs to clean up after a fight.
 */
public final class AbilityDamageTracker {

    /**
     * Damage sources created by a Safemode player. Synchronised because LOTM's factories can be reached from
     * more than one thread, and a {@link WeakHashMap} corrupts under concurrent writes.
     */
    private static final Set<DamageSource> SAFEMODE_ABILITY_SOURCES =
            Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    private AbilityDamageTracker() {
    }

    public static void markSafemodeAbilityDamage(DamageSource source) {
        if (source != null) {
            SAFEMODE_ABILITY_SOURCES.add(source);
        }
    }

    /** True when this damage came from a Beyonder ability used by a player on their Safemode profile. */
    public static boolean isSafemodeAbilityDamage(DamageSource source) {
        return source != null && SAFEMODE_ABILITY_SOURCES.contains(source);
    }
}
