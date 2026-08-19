package net.swimmingtuna.pathtodivinity.profile;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.swimmingtuna.pathtodivinity.network.PTDNetwork;
import net.swimmingtuna.pathtodivinity.network.ProfileVisibilityS2CPacket;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Keeps clients told which players are on which profile, so name tags can be marked.
 *
 * <p>The one place that decides what goes to whom. Everything here is a no-op when the profile system is
 * off, so a server running without it sends nothing and no client ever draws a tag.
 */
public final class ProfileVisibility {

    private ProfileVisibility() {
    }

    /** Sends the joining player the full roster, including themselves. */
    public static void syncAllTo(ServerPlayer joiner) {
        MinecraftServer server = joiner.getServer();
        if (!ProfileManager.isEnabled() || server == null) {
            return;
        }
        PlayerProfileData data = PlayerProfileData.get(server);
        Map<UUID, ProfileType> roster = new HashMap<>();
        for (ServerPlayer online : server.getPlayerList().getPlayers()) {
            ProfileType type = data.getActiveProfile(online.getUUID());
            if (type != null) {
                roster.put(online.getUUID(), type);
            }
        }
        PTDNetwork.sendToPlayer(ProfileVisibilityS2CPacket.snapshot(roster), joiner);
    }

    /**
     * Tells everyone one player's profile, or that they should be forgotten when {@code type} is null.
     *
     * <p>Sent to the subject too. Their own tag is invisible in first person, but the entry costs nothing and
     * keeps every client's map identical, which is one less thing to reason about.
     */
    public static void broadcast(ServerPlayer player, @Nullable ProfileType type) {
        if (!ProfileManager.isEnabled() || player.getServer() == null) {
            return;
        }
        PTDNetwork.sendToAll(ProfileVisibilityS2CPacket.single(player.getUUID(), type));
    }
}
