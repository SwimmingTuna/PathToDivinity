package net.swimmingtuna.pathtodivinity;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class PTDConfig {

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        Pair<Common, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = pair.getLeft();
        COMMON_SPEC = pair.getRight();
    }

    public static class Common {
        public ForgeConfigSpec.DoubleValue damageMultiplier;
        public ForgeConfigSpec.DoubleValue healthMultiplier;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("PTD Configs");

            damageMultiplier = builder
                    .comment("Use to multiply the amount of damage that mobs that are part of recipes deal. 1.0 = normal damage, decimals allowed (e.g. 0.5 = half, 2.5 = 2.5x).")
                    .defineInRange("Damage Multiplier", 0.85, 0.0, 10.0);

            healthMultiplier = builder
                    .comment("Use to multiply the amount of health that mobs that are part of recipes have. 1.0 = normal health, decimals allowed (e.g. 0.5 = half, 2.5 = 2.5x).")
                    .defineInRange("Health Multiplier", 0.85, 0.0, 10.0);

            builder.pop();
        }
    }
}