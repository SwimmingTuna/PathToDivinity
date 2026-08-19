package net.swimmingtuna.pathtodivinity.profile;

import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;

/**
 * The Curios half of a profile snapshot.
 *
 * <p>Every reference to Curios lives in this class and nowhere else, so the JVM only ever loads it on a server
 * that actually has Curios installed. {@link ProfileSnapshot} guards each call with its own {@code ModList}
 * check; do not call into here without one, and do not move these calls back inline.
 *
 * <p>Curios is deliberately left to notice the change itself. Its {@code CuriosEventHandler#tick} diffs each
 * slot against the previous stack once a tick, which is what applies and removes the curios' attribute
 * modifiers, fires the equip/unequip hooks and syncs the slots to the client — the same path a death and
 * respawn goes through.
 */
final class CuriosProfileBridge {

    private CuriosProfileBridge() {
    }

    /**
     * The player's curios, or {@code null} when they have no curios capability at all.
     *
     * <p>Saves without clearing: a switch can still be refused after the snapshot is taken, and a player who
     * was told "nothing was changed" should not find their rings gone.
     */
    @Nullable
    static ListTag capture(ServerPlayer player) {
        return CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.saveInventory(false))
                .orElse(null);
    }

    /**
     * Empties every curios slot, then fills them from {@code stored}.
     *
     * <p>The clear is not optional. Without it, switching into a profile that never wore a given curio would
     * leave the outgoing profile's item sitting in the slot, which is the leak this whole class exists to
     * close. Curios' own {@code saveInventory(true)} is the clear — it covers cosmetic slots too — and the
     * returned tag is thrown away because the caller has already captured the outgoing profile.
     */
    static void restore(ServerPlayer player, @Nullable ListTag stored) {
        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            handler.saveInventory(true);
            if (stored != null && !stored.isEmpty()) {
                handler.loadInventory(stored);
            }
        });
    }
}
