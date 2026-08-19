package net.swimmingtuna.pathtodivinity;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.swimmingtuna.lotm.client.Configs;
import net.swimmingtuna.pathtodivinity.network.PTDNetwork;
import org.slf4j.Logger;

import java.util.function.Supplier;

@Mod(PTD.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PTD {

    public static final int NEW_STRUCTURE_SIZE = 512;
    public static Supplier<Boolean> fadeOut;
    public static Supplier<Integer> fadeTicks;

    public static Supplier<Double> maxBrightness;
    public static Supplier<Double> fadeRate = () -> maxBrightness.get() / fadeTicks.get();

    public static final String MOD_ID = "pathtodivinity";
    public static final Logger LOGGER = LogUtils.getLogger();


    public PTD() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PTDConfig.COMMON_SPEC);
        MinecraftForge.EVENT_BUS.addListener(PTDCommands::onCommandRegistration);
        PTDNetwork.register();
    }

    /**
     * Re-sends the command tree to every online player when our config is edited in-game.
     *
     * <p>{@code /sequencelock} is gated behind the "Sequence Lock Enabled" flag via a Brigadier
     * {@code .requires} predicate, but the client only receives a filtered copy of the tree on login and on
     * op changes. Without this, flipping the config while the world is open would leave the command missing
     * from tab-completion until the player relogged.
     */
    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (!MOD_ID.equals(event.getConfig().getModId()) || event.getConfig().getType() != ModConfig.Type.COMMON) {
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        // Config files are reloaded off-thread; touching the player list has to happen on the server thread.
        server.execute(() -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                server.getCommands().sendCommands(player);
            }
        });
    }
}
