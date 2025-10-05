package net.swimmingtuna.pathtodivinity;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.curseforge.macabre.init.MacabreModEntities;
import com.eeeab.eeeabsmobs.sever.init.EntityInit;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.kyanite.deeperdarker.content.DDEntities;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import fuzs.mutantmonsters.init.ModRegistry;
import net.arphex.init.ArphexModEntities;
import net.cursedwarrior.awakenedbosses.init.AwakenedBossesModEntities;
import net.mcreator.animatedmobsmod.init.AnimatedmobsmodModEntities;
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
import net.zoniex.init.ZoniexModEntities;
import org.thecelestialworkshop.celestisynth.common.registry.CSItems;

import java.util.HashSet;
import java.util.Set;

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
        BEYONDER_ENTITY_TYPES.add(ACEntityRegistry.BRAINIAC.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.KOBOLEDIATOR.get());
        BEYONDER_ENTITY_TYPES.add(EntityHandler.UMVUTHI.get());
        BEYONDER_ENTITY_TYPES.add(AquamiraeEntities.MAW.get());
        //BEYONDER_ENTITY_TYPES.add(ArphexModEntities.ROACH_RIVERSPAWN.get());
        //BEYONDER_ENTITY_TYPES.add(ArphexModEntities.LONG_LEGS_FLY.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Skeletosaurus.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.NIGHTMARE_STALKER.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.GLUTTON_FISH.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SCORPION_STRIKER.get());
        BEYONDER_ENTITY_TYPES.add(ZoniexModEntities.BRUTALISER.get());
        BEYONDER_ENTITY_TYPES.add(EntityHandler.WROUGHTNAUT.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.DIRE_HOUND_LEADER.get());
        //Witness too but it shouldn't destroy blocks

        // Sequence 8 entities
        BEYONDER_ENTITY_TYPES.add(ModEntities.BlastCannon.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Frostbitten_Golem.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Endersent.get());
        BEYONDER_ENTITY_TYPES.add(ACEntityRegistry.GUM_WORM.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.DUSKROK.get());
        BEYONDER_ENTITY_TYPES.add(ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get());
        BEYONDER_ENTITY_TYPES.add(ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get());
        BEYONDER_ENTITY_TYPES.add(AMEntityRegistry.WARPED_MOSCO.get());
        BEYONDER_ENTITY_TYPES.add(EntityType.ELDER_GUARDIAN);
        BEYONDER_ENTITY_TYPES.add(ACEntityRegistry.TREMORSAURUS.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.SPIRITOF_CHAOS.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.MOTHER_SPIDER.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.HELLROK.get());
        BEYONDER_ENTITY_TYPES.add(ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get());
        BEYONDER_ENTITY_TYPES.add(AetherEntityTypes.VALKYRIE_QUEEN.get());


        // Sequence 7 entities
        BEYONDER_ENTITY_TYPES.add(ACEntityRegistry.FORSAKEN.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Ancient_Guardian.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_SNATCHER.get());
        BEYONDER_ENTITY_TYPES.add(MacabreModEntities.CRAWLER.get());  //Monolith
        BEYONDER_ENTITY_TYPES.add(EntityInit.CORPSE_WARLOCK.get());
        BEYONDER_ENTITY_TYPES.add(EntityHandler.FROSTMAW.get());
        BEYONDER_ENTITY_TYPES.add(AquamiraeEntities.MAZE_MOTHER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.CENTIPEDE_EVICTOR.get());
        BEYONDER_ENTITY_TYPES.add(DDEntities.STALKER.get());
        BEYONDER_ENTITY_TYPES.add(AnimatedmobsmodModEntities.ENDER_KING.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Withered_Abomination.get());
        BEYONDER_ENTITY_TYPES.add(AetherEntityTypes.SUN_SPIRIT.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SOLIFUGE_SKULKER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_GOLIATH.get());


        // Sequence 6 entities
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.NETHERITE_MONSTROSITY.get());
        BEYONDER_ENTITY_TYPES.add(ModEntityTypes.Spiritcaller.get());
        BEYONDER_ENTITY_TYPES.add(EntityType.WITHER);
        BEYONDER_ENTITY_TYPES.add(ModEntityTypes.Freakager.get());
        BEYONDER_ENTITY_TYPES.add(ModEntityTypes.Ragno.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.WASP_NEMESIS.get());
        BEYONDER_ENTITY_TYPES.add(ModEntityTypes.Magispeller.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_LURKER.get());
        BEYONDER_ENTITY_TYPES.add(AwakenedBossesModEntities.HEROBRINE.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.LIFESTEALER.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Lava_eater.get());
        BEYONDER_ENTITY_TYPES.add(MacabreModEntities.THE_HOLLOW_MAN.get());
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get());

        // Sequence 5 entities
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.THE_HARBINGER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.CRAB_CONSTRICTOR.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_REAPER.get());
        BEYONDER_ENTITY_TYPES.add(AquamiraeEntities.CAPTAIN_CORNELIA.get());
        BEYONDER_ENTITY_TYPES.add(ModEntities.Posessed_Paladin.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.ACCURSED_LORD_BOSS.get()); //Decaying King
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.RETURNING_KNIGHT.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_LUNGER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.MANTIS_MUTILATOR.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.ENDER_GUARDIAN.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.MOONKNIGHT.get()); //Fallen Icon
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.CHAOS_MONARCH.get()); //Monarch of Chaos
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.DRAUGR_BOSS.get()); //Old Champion's Remains
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.NIGHT_SHADE.get()); //Frenzied Shade

        // Sequence 4 entities
        BEYONDER_ENTITY_TYPES.add(ModEntities.Cloud_golem.get());
        BEYONDER_ENTITY_TYPES.add(ACEntityRegistry.HULLBREAKER.get());
        BEYONDER_ENTITY_TYPES.add(AMEntityRegistry.VOID_WORM.get());
        BEYONDER_ENTITY_TYPES.add(MacabreModEntities.GOMORIA.get());
        BEYONDER_ENTITY_TYPES.add(MacabreModEntities.GARGAMAW.get());
        BEYONDER_ENTITY_TYPES.add(MacabreModEntities.MORPHEGOR.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.IGNIS.get());
        BEYONDER_ENTITY_TYPES.add(MacabreModEntities.BAAL.get());
        BEYONDER_ENTITY_TYPES.add(ACEntityRegistry.LUXTRUCTOSAURUS.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.ANCIENT_ANCIENT_REMNANT.get());

        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.SCYLLA.get()); //ADD TO SEQUENCE 4
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.MALEDICTUS.get()); //ADD TO SEQUENCE 4

        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.GOB.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_PROWLER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_MATRIARCH.get());
        BEYONDER_ENTITY_TYPES.add(MacabreModEntities.VALAMON.get());
        BEYONDER_ENTITY_TYPES.add(com.github.L_Ender.cataclysm.init.ModEntities.THE_LEVIATHAN.get());
        BEYONDER_ENTITY_TYPES.add(EntityInit.NAMELESS_GUARDIAN.get());

        // Sequence 3 entities
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.MOONKNIGHT.get()); //Fallen Icon
        BEYONDER_ENTITY_TYPES.add(BornInChaosV1ModEntities.LORD_PUMPKINHEAD.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SPIDER_MOTH.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.TRIAL_GUARDIAN.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.DRACONIC_VOIDLASHER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SCORPIOID_BLOODLUSTER.get());

        // Sequence 2 entities
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.SUPER_SNIFFER.get());
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.GUNDALF.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.DAY_STALKER.get());
        BEYONDER_ENTITY_TYPES.add(EntityRegistry.NIGHT_PROWLER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.DRACONIC_VOIDLASHER.get());
        BEYONDER_ENTITY_TYPES.add(ArphexModEntities.SCORPIOID_BLOODLUSTER.get());

        // Sequence 1 entities
        BEYONDER_ENTITY_TYPES.add(TerramityModEntities.ULTRA_SNIFFER.get());
    }


    private static boolean matchesNameBasedConditions(Entity entity) {
        String entityName = entity.getName().getString().toLowerCase();
        String className = entity.getClass().getSimpleName(); //Comments are equal to sequence
        if (entityName.contains("vessel")) return true; //3
        if (entityName.equalsIgnoreCase("horseman")) return true;  //4
        if (entityName.contains("doomharbor")) return true; //7
        if (entityName.contains("terrible") || entityName.contains("puny")) return true; //8
        if (entityName.contains("plague_bringer")) return true; //7
        if (entityName.contains("aero_guardian")) return true; //8
        if (entityName.contains("dyrolian")) return true; //6
        if (className.equals("VoidBlossomEntity")) return true; //6
        if (className.equals("LichEntity")) return true; //7
        return className.equals("GauntletEntity"); //7
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
