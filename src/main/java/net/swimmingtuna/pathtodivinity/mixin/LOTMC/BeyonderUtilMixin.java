package net.swimmingtuna.pathtodivinity.mixin.LOTMC;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.eeeab.eeeabsmobs.sever.init.EntityInit;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import fuzs.mutantmonsters.init.ModRegistry;
import net.cursedwarrior.awakenedbosses.init.AwakenedBossesModEntities;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.mcreator.terramity.init.TerramityModEntities;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.soulsweaponry.registry.EntityRegistry;
import net.swimmingtuna.lotm.util.BeyonderUtil;
import net.swimmingtuna.pathtodivinity.PTDUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = BeyonderUtil.class, remap = false)
public class BeyonderUtilMixin {

    @ModifyVariable(
            method = "ageHandlerTick",
            at = @At(value = "LOAD", ordinal = 0),
            name = "maxAge"
    )
    private static int modifyMaxAgeForUltraSniffer(int maxAge, LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getType() == TerramityModEntities.ULTRA_SNIFFER.get()) {
            return 6000;
        }
        return maxAge;
    }

    private static final Map<EntityType<?>, Integer> ENTITY_SEQUENCE_MAP = new HashMap<>();

    static {
        initializeEntitySequenceMap();
    }

    private static void initializeEntitySequenceMap() {
        // Sequence 9 entities
        ENTITY_SEQUENCE_MAP.put(ModEntities.Overgrown_colossus.get(), 9);
        ENTITY_SEQUENCE_MAP.put(ModEntities.Warped_Fungussus.get(), 9);
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.KOBOLEDIATOR.get(), 9);
        ENTITY_SEQUENCE_MAP.put(EntityHandler.UMVUTHI.get(), 9);
        ENTITY_SEQUENCE_MAP.put(AquamiraeEntities.MAW.get(), 9);
        ENTITY_SEQUENCE_MAP.put(ModEntities.Skeletosaurus.get(), 9);
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.NIGHTMARE_STALKER.get(), 9);
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.GLUTTON_FISH.get(), 9);
        ENTITY_SEQUENCE_MAP.put(EntityHandler.WROUGHTNAUT.get(), 9);
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.DIRE_HOUND_LEADER.get(), 9);

        // Sequence 8 entities
        ENTITY_SEQUENCE_MAP.put(ModEntities.BlastCannon.get(), 8);
        ENTITY_SEQUENCE_MAP.put(ModEntities.Frostbitten_Golem.get(), 8);
        ENTITY_SEQUENCE_MAP.put(ModEntities.Endersent.get(), 8);
        ENTITY_SEQUENCE_MAP.put(TerramityModEntities.DUSKROK.get(), 8);
        ENTITY_SEQUENCE_MAP.put(ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get(), 8);
        ENTITY_SEQUENCE_MAP.put(ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get(), 8);
        //ENTITY_SEQUENCE_MAP.put(AMEntityRegistry.WARPED_MOSCO.get(), 8);
        ENTITY_SEQUENCE_MAP.put(EntityType.ELDER_GUARDIAN, 8);
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.SPIRITOF_CHAOS.get(), 8);
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.MOTHER_SPIDER.get(), 8);
        ENTITY_SEQUENCE_MAP.put(TerramityModEntities.HELLROK.get(), 8);
        ENTITY_SEQUENCE_MAP.put(ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get(), 8);

        // Sequence 7 entities
        ENTITY_SEQUENCE_MAP.put(ModEntities.Ancient_Guardian.get(), 7);
        ENTITY_SEQUENCE_MAP.put(EntityInit.CORPSE_WARLOCK.get(), 7);
        ENTITY_SEQUENCE_MAP.put(EntityHandler.FROSTMAW.get(), 7);
        ENTITY_SEQUENCE_MAP.put(AquamiraeEntities.MAZE_MOTHER.get(), 7);
        ENTITY_SEQUENCE_MAP.put(ModEntities.Withered_Abomination.get(), 7);

        // Sequence 6 entities
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.NETHERITE_MONSTROSITY.get(), 6);
        ENTITY_SEQUENCE_MAP.put(EntityType.WITHER, 6);
        ENTITY_SEQUENCE_MAP.put(AwakenedBossesModEntities.HEROBRINE.get(), 6);
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.LIFESTEALER.get(), 6);
        ENTITY_SEQUENCE_MAP.put(ModEntities.Lava_eater.get(), 6);
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get(), 6);

        // Sequence 5 entities
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.THE_HARBINGER.get(), 5);
        ENTITY_SEQUENCE_MAP.put(AquamiraeEntities.CAPTAIN_CORNELIA.get(), 5);
        ENTITY_SEQUENCE_MAP.put(ModEntities.Posessed_Paladin.get(), 5);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.ACCURSED_LORD_BOSS.get(), 5);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.RETURNING_KNIGHT.get(), 5);
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.ENDER_GUARDIAN.get(), 5);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.MOONKNIGHT.get(), 5);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.CHAOS_MONARCH.get(), 5);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.DRAUGR_BOSS.get(), 5);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.NIGHT_SHADE.get(), 5);

        // Sequence 4 entities
        ENTITY_SEQUENCE_MAP.put(ModEntities.Cloud_golem.get(), 4);
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.IGNIS.get(), 4);
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.SCYLLA.get(), 4);
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.MALEDICTUS.get(), 4);
        ENTITY_SEQUENCE_MAP.put(TerramityModEntities.GOB.get(), 4);
        ENTITY_SEQUENCE_MAP.put(com.github.L_Ender.cataclysm.init.ModEntities.THE_LEVIATHAN.get(), 4);
        ENTITY_SEQUENCE_MAP.put(EntityInit.NAMELESS_GUARDIAN.get(), 4);

        // Sequence 3 entities
        ENTITY_SEQUENCE_MAP.put(BornInChaosV1ModEntities.LORD_PUMPKINHEAD.get(), 3);
        ENTITY_SEQUENCE_MAP.put(TerramityModEntities.TRIAL_GUARDIAN.get(), 3);

        // Sequence 2 entities
        ENTITY_SEQUENCE_MAP.put(TerramityModEntities.SUPER_SNIFFER.get(), 2);
        ENTITY_SEQUENCE_MAP.put(TerramityModEntities.GUNDALF.get(), 2);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.DAY_STALKER.get(), 2);
        ENTITY_SEQUENCE_MAP.put(EntityRegistry.NIGHT_PROWLER.get(), 2);

        // Sequence 1 entities
        ENTITY_SEQUENCE_MAP.put(TerramityModEntities.ULTRA_SNIFFER.get(), 1);
    }

    @Inject(method = "getSequence", at = @At("HEAD"), cancellable = true)
    private static void injectCustomSequences(LivingEntity living, CallbackInfoReturnable<Integer> cir) {
        if (living == null) {
            cir.setReturnValue(10);
            return;
        }
        Integer customSequence = ENTITY_SEQUENCE_MAP.get(living.getType());
        if (customSequence != null) {
            cir.setReturnValue(customSequence);
            return;
        }
        // LOTM calls getSequence constantly, so the name checks below go through PTDUtil's
        // cached trait bitmask rather than decomposing the display name Component every call.
        int traits = PTDUtil.nameTraits(living);

        if (living instanceof Mob) {
            if ((traits & PTDUtil.TRAIT_VESSEL) != 0) {
                cir.setReturnValue(3);
                return;
            }
            if ((traits & PTDUtil.TRAIT_HORSEMAN) != 0) {
                cir.setReturnValue(4);
                return;
            }
            if ((traits & PTDUtil.TRAIT_DOOMHARBOR) != 0) {
                cir.setReturnValue(7);
                return;
            }
            if ((traits & (PTDUtil.TRAIT_TERRIBLE | PTDUtil.TRAIT_PUNY)) != 0) {
                cir.setReturnValue(8);
                return;
            }
            if ((traits & PTDUtil.TRAIT_PLAGUE_BRINGER) != 0) {
                cir.setReturnValue(7);
                return;
            }
            if ((traits & PTDUtil.TRAIT_AERO_GUARDIAN) != 0) {
                cir.setReturnValue(8);
                return;
            }
            if ((traits & PTDUtil.TRAIT_DYROLIAN) != 0) {
                cir.setReturnValue(6);
                return;
            }
        }
        if ((traits & PTDUtil.TRAIT_VOID_BLOSSOM) != 0) {
            cir.setReturnValue(6);
            return;
        }
        if ((traits & PTDUtil.TRAIT_LICH) != 0) {
            cir.setReturnValue(7);
            return;
        }
        if ((traits & PTDUtil.TRAIT_GAUNTLET) != 0) {
            cir.setReturnValue(7);
            return;
        }
    }
}