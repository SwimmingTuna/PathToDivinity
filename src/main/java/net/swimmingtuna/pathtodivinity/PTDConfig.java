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
        public ForgeConfigSpec.BooleanValue sequenceLockEnabled;
        public ForgeConfigSpec.BooleanValue profilesEnabled;
        public ForgeConfigSpec.BooleanValue normalProfileRegresses;
        public ForgeConfigSpec.IntValue regressionGraceMinutes;
        public ForgeConfigSpec.IntValue safemodeMaxSequence;
        public ForgeConfigSpec.DoubleValue safemodeAbilityDamage;
        public ForgeConfigSpec.IntValue profileSwitchCooldownMinutes;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("PTD Configs");

            damageMultiplier = builder
                    .comment("Use to multiply the amount of damage that mobs that are part of recipes deal. 1.0 = normal damage, decimals allowed (e.g. 0.5 = half, 2.5 = 2.5x).")
                    .defineInRange("Damage Multiplier", 0.85, 0.0, 10.0);

            healthMultiplier = builder
                    .comment("Use to multiply the amount of health that mobs that are part of recipes have. 1.0 = normal health, decimals allowed (e.g. 0.5 = half, 2.5 = 2.5x).")
                    .defineInRange("Health Multiplier", 0.85, 0.0, 10.0);

            sequenceLockEnabled = builder
                    .comment("Enables the /sequencelock command, which caps how far players can advance down the",
                            "Beyonder sequence ladder. Off by default so normal play is unaffected.")
                    .define("Sequence Lock Enabled", false);

            profilesEnabled = builder
                    .comment("Enables the Beyonder profile system: every player keeps two completely separate saves,",
                            "a Normal profile and a Safemode profile, chosen when they first drink a Sequence 9 potion",
                            "and swapped with /beyonderprofile. Off by default so normal play is unaffected.")
                    .define("Profiles Enabled", false);

            normalProfileRegresses = builder
                    .comment("Whether a player on the Normal profile loses a sequence when killed by another player.",
                            "This is Path to Divinity's own regression, independent of LOTM's 'Should Drop Characteristic'.",
                            "Safemode profiles are never regressed this way regardless of this setting.")
                    .define("Normal Profile Regresses On Death", true);

            regressionGraceMinutes = builder
                    .comment("Minutes a Normal profile is immune from further player-kill regression after losing",
                            "a sequence. No characteristic drops during this window either, so two players cannot",
                            "trade kills to farm them. Set to 0 to turn the window off entirely - handy for testing,",
                            "but it does make the drop farmable. Safemode's protection is not affected by this.")
                    .defineInRange("Regression Grace Minutes", 10, 0, 10000);

            safemodeMaxSequence = builder
                    .comment("The strongest sequence a Safemode profile may advance to. Sequences run 9 (weakest) down",
                            "to 0 (strongest), so the default of 1 lets Safemode reach Sequence 1 but never Sequence 0.")
                    .defineInRange("Safemode Max Sequence", 1, 0, 9);

            safemodeAbilityDamage = builder
                    .comment("Multiplier applied to Beyonder ability damage dealt BY a Safemode player TO another player.",
                            "1.0 = no reduction, 0.5 = half damage. Damage dealt to mobs is never reduced, and damage",
                            "dealt to a Safemode player is not affected.")
                    .defineInRange("Safemode Ability Damage vs Players", 0.5, 0.0, 1.0);

            profileSwitchCooldownMinutes = builder
                    .comment("Minutes a player must wait between profile switches. Switching is always blocked while",
                            "they are in combat. Set to 0 to disable the cooldown.")
                    .defineInRange("Profile Switch Cooldown Minutes", 60, 0, 100000);

            builder.pop();
        }
    }
}