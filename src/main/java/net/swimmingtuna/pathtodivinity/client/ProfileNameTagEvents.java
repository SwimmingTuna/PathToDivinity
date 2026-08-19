package net.swimmingtuna.pathtodivinity.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.swimmingtuna.pathtodivinity.PTD;

/**
 * Client-side lifecycle for the profile visibility roster.
 *
 * <p>Safemode players render with their ordinary name tag — no marker is appended.
 */
@Mod.EventBusSubscriber(modid = PTD.MOD_ID, value = Dist.CLIENT)
public class ProfileNameTagEvents {

    @SubscribeEvent
    public static void forgetRosterOnDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        ProfileVisibilityCache.clear();
    }
}
