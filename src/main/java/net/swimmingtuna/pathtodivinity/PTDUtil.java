package net.swimmingtuna.pathtodivinity;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.eeeab.eeeabsmobs.sever.init.EntityInit;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import fuzs.mutantmonsters.init.ModRegistry;
import net.cursedwarrior.awakenedbosses.init.AwakenedBossesModEntities;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.mcreator.terramity.init.TerramityModEntities;
import net.mcreator.terramity.init.TerramityModItems;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.soulsweaponry.registry.EntityRegistry;
import net.swimmingtuna.lotm.util.BeyonderUtil;
import org.thecelestialworkshop.celestisynth.common.registry.CSItems;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PTDUtil {

    // Create a static set of all beyonder entity types for efficient lookup
    private static final Set<EntityType<?>> BEYONDER_ENTITY_TYPES = new HashSet<>();

    static {
        // Initialize the set with all beyonder entity types
        initializeBeyonderEntityTypes();
    }

    private static void initializeBeyonderEntityTypes() {
        // Sequence 9 entities
        BEYONDER_ENTITY_TYPES.add(ModEntities.Overgrown_colossus.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Warped_Fungussus.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.KOBOLEDIATOR.get());
        BEYONDER_ENTITY_TYPES.add(EntityHandler.UMVUTHI.get());
        BEYONDER_ENTITY_TYPES.add(AquamiraeEntities.MAW.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Skeletosaurus.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.NIGHTMARE_STALKER.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.GLUTTON_FISH.get());
        BEYONDER_ENTITY_TYPES.add(EntityHandler.WROUGHTNAUT.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.DIRE_HOUND_LEADER.get());
        //Witness too but it shouldn't destroy blocks

        // Sequence 8 entities
        BEYONDER_ENTITY_TYPES.add(ModEntities.BlastCannon.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Frostbitten_Golem.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Endersent.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.DUSKROK.get());
        BEYONDER_ENTITY_TYPES.add(ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get());
        BEYONDER_ENTITY_TYPES.add(ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get());
        //BEYONDER_ENTITY_TYPES.add(AMEntityRegistry.WARPED_MOSCO.get());
        BEYONDER_ENTITY_TYPES.add(EntityType.ELDER_GUARDIAN);
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.SPIRITOF_CHAOS.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.MOTHER_SPIDER.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.HELLROK.get());
        BEYONDER_ENTITY_TYPES.add(ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get());


        // Sequence 7 entities
        BEYONDER_ENTITY_TYPES.add(ModEntities.Ancient_Guardian.get());
        BEYONDER_ENTITY_TYPES.add(EntityInit.CORPSE_WARLOCK.get());
        BEYONDER_ENTITY_TYPES.add(EntityHandler.FROSTMAW.get());
        BEYONDER_ENTITY_TYPES.add(AquamiraeEntities.MAZE_MOTHER.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Withered_Abomination.get());
        BEYONDER_ENTITY_TYPES.add(net.swimmingtuna.lotm.init.EntityInit.ASMANN.get());


        // Sequence 6 entities
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.NETHERITE_MONSTROSITY.get());
        BEYONDER_ENTITY_TYPES.add(EntityType.WITHER);
        BEYONDER_ENTITY_TYPES.add(AwakenedBossesModEntities.HEROBRINE.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.LIFESTEALER.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Lava_eater.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get());
        BEYONDER_ENTITY_TYPES.add(net.swimmingtuna.lotm.init.EntityInit.SHADOWLESS_DEMONIC_WOLF.get());

        // Sequence 5 entities
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.THE_HARBINGER.get());
        BEYONDER_ENTITY_TYPES.add(AquamiraeEntities.CAPTAIN_CORNELIA.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Posessed_Paladin.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.ACCURSED_LORD_BOSS.get()); //Decaying King
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.RETURNING_KNIGHT.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.ENDER_GUARDIAN.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.MOONKNIGHT.get()); //Fallen Icon
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.CHAOS_MONARCH.get()); //Monarch of Chaos
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.DRAUGR_BOSS.get()); //Old Champion's Remains
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.NIGHT_SHADE.get()); //Frenzied Shade
        BEYONDER_ENTITY_TYPES.add(net.swimmingtuna.lotm.init.EntityInit.DRAGON.get());

        // Sequence 4 entities
        BEYONDER_ENTITY_TYPES.add(ModEntities.Cloud_golem.get());
        //BEYONDER_ENTITY_TYPES.add(AMEntityRegistry.VOID_WORM.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.IGNIS.get());

        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.SCYLLA.get()); //ADD TO SEQUENCE 4
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.MALEDICTUS.get()); //ADD TO SEQUENCE 4

        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.GOB.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.THE_LEVIATHAN.get());
        BEYONDER_ENTITY_TYPES.add(EntityInit.NAMELESS_GUARDIAN.get());

        // Sequence 3 entities
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.MOONKNIGHT.get()); //Fallen Icon
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.LORD_PUMPKINHEAD.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.TRIAL_GUARDIAN.get());

        // Sequence 2 entities
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.SUPER_SNIFFER.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.GUNDALF.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.DAY_STALKER.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.NIGHT_PROWLER.get());

        // Sequence 1 entities
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.ULTRA_SNIFFER.get());
        BEYONDER_ENTITY_TYPES.add(net.swimmingtuna.lotm.init.EntityInit.INTERDIMENSIONAL_HUNTER.get());
    }


    // Some mobs are identified by their display name or class name rather than by EntityType,
    // because the mods they come from either register several bosses under one type or expose no
    // usable registry object. Deriving that from Component#getString every time is expensive:
    // for a mob without a custom name it decomposes a TranslatableContents into a fresh
    // StringBuilder and String, and toLowerCase allocates another. Doing that per entity per tick
    // was the mod's single largest source of garbage on the server, so the answer is cached below
    // as a bitmask. Comments are equal to sequence.
    public static final int TRAIT_VESSEL = 1;               //3
    public static final int TRAIT_HORSEMAN = 1 << 1;        //4
    public static final int TRAIT_DOOMHARBOR = 1 << 2;      //7
    public static final int TRAIT_TERRIBLE = 1 << 3;        //8
    public static final int TRAIT_PUNY = 1 << 4;            //8
    public static final int TRAIT_TERRIBLE_TEN = 1 << 5;
    public static final int TRAIT_PLAGUE_BRINGER = 1 << 6;  //7
    public static final int TRAIT_AERO_GUARDIAN = 1 << 7;   //8
    public static final int TRAIT_DYROLIAN = 1 << 8;        //6
    public static final int TRAIT_VOID_BLOSSOM = 1 << 9;    //6
    public static final int TRAIT_LICH = 1 << 10;           //7
    public static final int TRAIT_GAUNTLET = 1 << 11;       //7
    public static final int TRAIT_OBSIDILITH = 1 << 12;

    /** Traits that on their own qualify an entity as a Beyonder entity. */
    private static final int BEYONDER_NAME_TRAITS =
            TRAIT_VESSEL | TRAIT_HORSEMAN | TRAIT_DOOMHARBOR | TRAIT_TERRIBLE | TRAIT_PUNY
                    | TRAIT_PLAGUE_BRINGER | TRAIT_AERO_GUARDIAN | TRAIT_DYROLIAN
                    | TRAIT_VOID_BLOSSOM | TRAIT_LICH | TRAIT_GAUNTLET;

    // ConcurrentHashMap rather than HashMap: BeyonderUtil#getSequence reaches nameTraits from the
    // client thread as well as the server thread.
    private static final Map<EntityType<?>, Integer> TYPE_NAME_TRAITS = new ConcurrentHashMap<>();

    /**
     * Name- and class-based traits of an entity, as a bitmask of the TRAIT_* constants.
     *
     * <p>For an entity without a custom name the traits are a pure function of its EntityType, so
     * the name is decomposed once per type for the lifetime of the server. Custom-named entities
     * are re-evaluated every call, so renaming a mob with a name tag is still picked up.
     */
    public static int nameTraits(Entity entity) {
        if (entity.hasCustomName()) {
            return computeNameTraits(entity);
        }
        return TYPE_NAME_TRAITS.computeIfAbsent(entity.getType(), type -> computeNameTraits(entity));
    }

    /** True when the entity has any of the traits in {@code mask}. */
    public static boolean hasTrait(Entity entity, int mask) {
        return (nameTraits(entity) & mask) != 0;
    }

    private static int computeNameTraits(Entity entity) {
        String entityName = entity.getName().getString().toLowerCase(Locale.ROOT);
        String className = entity.getClass().getSimpleName();
        int traits = 0;
        if (entityName.contains("vessel")) traits |= TRAIT_VESSEL;
        if (entityName.equals("horseman")) traits |= TRAIT_HORSEMAN;
        if (entityName.contains("doomharbor")) traits |= TRAIT_DOOMHARBOR;
        if (entityName.contains("terrible")) traits |= TRAIT_TERRIBLE;
        if (entityName.contains("puny")) traits |= TRAIT_PUNY;
        if (entityName.contains("terrible_ten")) traits |= TRAIT_TERRIBLE_TEN;
        if (entityName.contains("plague_bringer")) traits |= TRAIT_PLAGUE_BRINGER;
        if (entityName.contains("aero_guardian")) traits |= TRAIT_AERO_GUARDIAN;
        if (entityName.contains("dyrolian")) traits |= TRAIT_DYROLIAN;
        if (className.equals("VoidBlossomEntity")) traits |= TRAIT_VOID_BLOSSOM;
        if (className.equals("LichEntity")) traits |= TRAIT_LICH;
        if (className.equals("GauntletEntity")) traits |= TRAIT_GAUNTLET;
        if (className.equals("ObsidilithEntity")) traits |= TRAIT_OBSIDILITH;
        return traits;
    }

    private static boolean matchesNameBasedConditions(Entity entity) {
        return (nameTraits(entity) & BEYONDER_NAME_TRAITS) != 0;
    }

    // Main method that checks both Set and name-based conditions
    public static boolean isBeyonderEntity(Entity entity) {
        if (BEYONDER_ENTITY_TYPES.contains(entity.getType())) {
            return true;
        }
        return matchesNameBasedConditions(entity);
    }

    public static boolean isBeyonderEntity(EntityType<?> entityType) {
        return BEYONDER_ENTITY_TYPES.contains(entityType);
    }


    public static boolean isBannableItem(ItemStack itemStack) {
        return

                itemStack.is(TerramityModItems.ULTRA_SNIFFER_FUR.get()) ||
                        itemStack.is(TerramityModItems.POKER_CHIP_BRACELETS.get()) ||
                        itemStack.is(TerramityModItems.FATEFUL_COIN.get()) ||
                        itemStack.is(TerramityModItems.LUCKY_DICE.get()) ||
                        itemStack.is(TerramityModItems.ULTRA_SNIFFER_FUR.get()) ||

                        itemStack.is(ModItems.CURSIUM_CHESTPLATE.get());
        //DyrolianSword

    }


    public static void removeBannedItem(LivingEntity living) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR || slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
                ItemStack itemStack = living.getItemBySlot(slot);
                if (isBannableItem(itemStack)) {
                    living.setItemSlot(slot, ItemStack.EMPTY);
                    living.sendSystemMessage(Component.literal("Banned item removed: " + itemStack.getHoverName().getString()).withStyle(ChatFormatting.RED));
                }
            }
            if (slot.getType() == EquipmentSlot.Type.HAND) {
                ItemStack itemStack = living.getItemBySlot(slot);
                if (BeyonderUtil.getSequence(living) > 4) {
                    if (isBannableSequence5Item(itemStack)) {
                        if (living instanceof Player player) {
                            boolean moved = false;
                            if (player.getInventory().add(itemStack)) {
                                moved = true;
                            } else {
                                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                                    if (player.getInventory().getItem(i).isEmpty()) {
                                        player.getInventory().setItem(i, itemStack);
                                        moved = true;
                                        break;
                                    }
                                }
                            }
                            if (moved) {
                                living.setItemSlot(slot, ItemStack.EMPTY);
                                living.sendSystemMessage(Component.literal("Item moved to inventory: " + itemStack.getHoverName().getString() + " (Requires Sequence 4 or higher)").withStyle(ChatFormatting.YELLOW));
                            } else {
                                living.setItemSlot(slot, ItemStack.EMPTY);
                                player.drop(itemStack, false);
                                living.sendSystemMessage(Component.literal("Item dropped (inventory full): " + itemStack.getHoverName().getString() + " (Requires Sequence 4 or higher)").withStyle(ChatFormatting.GOLD));
                            }
                        }
                    }
                }
            }
        }
        if (living instanceof Player player) {
            Inventory inventory = player.getInventory();
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack itemStack = inventory.getItem(i);
                if (isBannableItem(itemStack)) {
                    inventory.setItem(i, ItemStack.EMPTY);
                    living.sendSystemMessage(Component.literal("Banned item removed from inventory: " + itemStack.getHoverName().getString()).withStyle(ChatFormatting.RED));
                } else if (BeyonderUtil.getSequence(living) > 4 && isBannableSequence5Item(itemStack)) {
                    inventory.setItem(i, ItemStack.EMPTY);
                    player.drop(itemStack, false);
                    living.sendSystemMessage(Component.literal("Item dropped from inventory: " + itemStack.getHoverName().getString() + " (Requires Sequence 4 or higher)").withStyle(ChatFormatting.GOLD));
                } else if (itemStack.is(TerramityModItems.MUSIC_SHEET_OF_UNTIMELY_DEATH.get())) {
                    inventory.setItem(i, ItemStack.EMPTY);
                }
            }
            ItemStack offhandStack = inventory.offhand.get(0);
            if (isBannableItem(offhandStack)) {
                inventory.offhand.set(0, ItemStack.EMPTY);
                living.sendSystemMessage(Component.literal("Banned item removed from offhand: " + offhandStack.getHoverName().getString()).withStyle(ChatFormatting.RED));
            } else if (BeyonderUtil.getSequence(living) > 4 && isBannableSequence5Item(offhandStack)) {
                inventory.offhand.set(0, ItemStack.EMPTY);
                player.drop(offhandStack, false);
                living.sendSystemMessage(Component.literal("Item dropped from offhand: " + offhandStack.getHoverName().getString() + " (Requires Sequence 4 or higher)").withStyle(ChatFormatting.GOLD));
            }
        }
    }

    public static boolean isBannableSequence5Item(ItemStack stack) {
        return
                stack.is(CSItems.AQUAFLORA.get()) ||
                        stack.is(CSItems.KERES.get()) ||
                        stack.is(CSItems.BREEZEBREAKER.get()) ||
                        stack.is(CSItems.SOLARIS.get()) ||
                        stack.is(CSItems.CRESCENTIA.get()) ||
                        stack.is(CSItems.POLTERGEIST.get()) ||
                        stack.is(CSItems.AQUAFLORA.get()) ||
                        stack.is(CSItems.RAINFALL_SERENITY.get()) ||
                        stack.is(CSItems.FROSTBOUND.get());
    }
}
