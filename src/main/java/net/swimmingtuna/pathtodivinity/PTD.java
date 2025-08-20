package net.swimmingtuna.pathtodivinity;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
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
        MinecraftForge.EVENT_BUS.register(new PTDGameRules());
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(PTDCommands::onCommandRegistration);
    }
}
