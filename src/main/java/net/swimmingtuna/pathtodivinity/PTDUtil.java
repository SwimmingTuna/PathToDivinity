package net.swimmingtuna.pathtodivinity;

import com.curseforge.macabre.init.MacabreModItems;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.arphex.init.ArphexModItems;
import net.mcreator.terramity.init.TerramityModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class PTDUtil {

    public static boolean isBeyonderEntity(String entityName) {
        String name = entityName.toLowerCase();
        return name.contains("cyclops") ||
                name.contains("overgrown colossus") ||
                name.contains("brainiac") ||
                name.contains("dune sentinel") ||
                name.contains("frostbitten golem") ||
                name.contains("gum worm") ||
                name.contains("duskrok") ||
                name.contains("mutant skeleton") ||
                name.contains("forsaken") ||
                name.contains("ancient guardian") ||
                name.contains("spider snatcher") ||
                name.contains("monolith") ||
                name.contains("doomharbor lich") ||
                name.contains("keeper of souls") ||
                name.contains("netherite monstrosity") ||
                name.contains("spirit caller") ||
                name.contains("wither") ||
                name.contains("void blossom") ||
                name.contains("freakager") ||
                name.contains("monarch of chaos") ||
                name.contains("harbinger") ||
                name.contains("crab constrictor") ||
                name.contains("hullbreaker") ||
                name.contains("ender king") ||
                name.contains("gargamaw") ||
                name.contains("grotesque consumer") ||
                name.contains("ignis") ||
                name.contains("cloud golem") ||
                name.contains("spider reaper") ||
                name.contains("fire dragon") ||
                name.contains("vessel of calamity") ||
                name.contains("ice dragon") ||
                name.contains("spider moth") ||
                name.contains("super sniffer") ||
                name.contains("day stalker") ||
                name.contains("ultrasniffer") ||
                name.contains("kobolediator") ||
                name.contains("umvuithi") ||
                name.contains("sunbird") ||
                name.contains("maw") ||
                name.contains("roach riverspawn") ||
                name.contains("long legs fly") ||
                name.contains("blocknight") ||
                name.contains("warped mosco") ||
                name.contains("sea serpent") ||
                name.contains("elder guardian") ||
                name.contains("mutated enderman") ||
                name.contains("corpse warlock") ||
                name.contains("frostmaw") ||
                name.contains("mother of the maze") ||
                name.contains("centipede evictor") ||
                name.contains("plaguebringer") ||
                name.contains("wasp nemesis") ||
                name.contains("magispeller") ||
                name.contains("captain cornelia") ||
                name.contains("old champion") ||
                name.contains("decaying king") ||
                name.contains("baal") ||
                name.contains("motionless calamity") ||
                name.contains("luxtructosaurus") ||
                name.contains("leviathan") ||
                name.contains("gnob") ||
                name.contains("gnome king") ||
                name.contains("fallen icon") ||
                name.contains("draconic voidlasher") ||
                name.contains("lightning dragon") ||
                name.contains("night prowler") ||
                name.contains("skeletosaurus") ||
                name.contains("nightmare") ||
                name.contains("witness") ||
                name.contains("scorpion striker") ||
                name.contains("aero guardian") ||
                name.contains("tremorsaurous") ||
                name.contains("spirit of chaos") ||
                name.contains("spiders mother") ||
                name.contains("spider goliath") ||
                name.contains("stalker") ||
                name.contains("night lich") ||
                name.contains("withered abomination") ||
                name.contains("herobrine") ||
                name.contains("dyrolian") ||
                name.contains("faded king") ||
                name.contains("lifestealer") ||
                name.contains("lava eater") ||
                name.contains("returning knight") ||
                name.contains("gomoria") ||
                name.contains("fleshmonger monk") ||
                name.contains("spider prowler") ||
                name.contains("pumpkin horseman") ||
                name.contains("lord pumpkinhead") ||
                name.contains("scorpiod bloodluster") ||
                name.contains("archmage gundalf") ||
                name.contains("zombie brutaliser") ||
                name.contains("ferrous wroughtnaught") ||
                name.contains("dire hound leader") ||
                name.contains("valkyrie queen") ||
                name.contains("hydra") ||
                name.contains("hellrok") ||
                name.contains("mutant zombie") ||
                name.contains("blindballoon") ||
                name.contains("sun god") ||
                name.contains("nether gauntlet") ||
                name.contains("solifuge skulker") ||
                name.contains("obsidilith") ||
                name.contains("hollow man") ||
                name.contains("sir pumpkinhead") ||
                name.contains("ender guardian") ||
                name.contains("valamon") ||
                name.contains("corpse butcher") ||
                name.contains("nameless guardian") ||
                name.contains("trial guardian");
    }

    public static boolean isBannableItem(ItemStack itemStack) {
        return
                itemStack.is(IafItemRegistry.DRAGONSTEEL_ICE_SWORD.get()) ||
                        itemStack.is(IafItemRegistry.DRAGONSTEEL_FIRE_SWORD.get()) ||
                        itemStack.is(IafItemRegistry.DRAGONSTEEL_LIGHTNING_SWORD.get()) ||
                        itemStack.is(MacabreModItems.ABHORRENT_SWORD.get()) ||
                        itemStack.is(MacabreModItems.ABHORRENT_AXE.get()) ||
                        itemStack.is(MacabreModItems.BLOOD_CLOT_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.BLOOD_CLOT_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.BLOOD_CLOT_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.BLOOD_CLOT_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.SYMBIOTIC_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.SYMBIOTIC_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.SYMBIOTIC_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.SYMBIOTIC_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.PLASMA_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.PLASMA_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.PLASMA_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.PLASMA_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.FERRUM_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.FERRUM_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.FERRUM_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.FERRUM_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.ABHORRENT_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.ABHORRENT_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.ABHORRENT_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.ABHORRENT_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.BAAL_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.BAAL_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.BAAL_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.BAAL_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.GOMORIA_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.GOMORIA_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.GOMORIA_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.GOMORIA_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.MORPHEGOR_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.MORPHEGOR_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.MORPHEGOR_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.MORPHEGOR_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.GARGAMAW_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.GARGAMAW_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.GARGAMAW_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.GARGAMAW_ARMOR_BOOTS.get()) ||
                        itemStack.is(MacabreModItems.VALAMON_ARMOR_HELMET.get()) ||
                        itemStack.is(MacabreModItems.VALAMON_ARMOR_CHESTPLATE.get()) ||
                        itemStack.is(MacabreModItems.VALAMON_ARMOR_LEGGINGS.get()) ||
                        itemStack.is(MacabreModItems.VALAMON_ARMOR_BOOTS.get()) ||
                        itemStack.is(ArphexModItems.INFERNAL_HELMET.get()) ||
                        itemStack.is(ArphexModItems.INFERNAL_CHESTPLATE.get()) ||
                        itemStack.is(ArphexModItems.INFERNAL_LEGGINGS.get()) ||
                        itemStack.is(ArphexModItems.INFERNAL_BOOTS.get()) ||
                        itemStack.is(ArphexModItems.SPECTRAL_HELMET.get()) ||
                        itemStack.is(ArphexModItems.SPECTRAL_CHESTPLATE.get()) ||
                        itemStack.is(ArphexModItems.SPECTRAL_LEGGINGS.get()) ||
                        itemStack.is(ArphexModItems.SPECTRAL_BOOTS.get()) ||
                        itemStack.is(ArphexModItems.UMBRAL_HELMET.get()) ||
                        itemStack.is(ArphexModItems.UMBRAL_CHESTPLATE.get()) ||
                        itemStack.is(ArphexModItems.UMBRAL_LEGGINGS.get()) ||
                        itemStack.is(ArphexModItems.UMBRAL_BOOTS.get()) ||
                        itemStack.is(ArphexModItems.JUGGERNAUT_HELMET.get()) ||
                        itemStack.is(ArphexModItems.JUGGERNAUT_CHESTPLATE.get()) ||
                        itemStack.is(ArphexModItems.JUGGERNAUT_LEGGINGS.get()) ||
                        itemStack.is(ArphexModItems.JUGGERNAUT_BOOTS.get()) ||
                        itemStack.is(ArphexModItems.TORMENTED_WRATH.get()) ||
                        itemStack.is(ArphexModItems.ABYSS_ASCENDANT.get()) ||
                        itemStack.is(TerramityModItems.ULTRA_SNIFFER_FUR.get()) ||

                        itemStack.is(TerramityModItems.POKER_CHIP_BRACELETS.get()) ||
                        itemStack.is(TerramityModItems.FATEFUL_COIN.get()) ||
                        itemStack.is(TerramityModItems.LUCKY_DICE.get()) ||
                        itemStack.is(TerramityModItems.ULTRA_SNIFFER_FUR.get()) ||

                        itemStack.is(ModItems.CURSIUM_CHESTPLATE.get());



    }

    public static void removeBannedItem(LivingEntity living) {
        // Check armor slots
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR || slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
                ItemStack itemStack = living.getItemBySlot(slot);
                if (isBannableItem(itemStack)) {
                    living.setItemSlot(slot, ItemStack.EMPTY);
                    living.sendSystemMessage(Component.literal("Banned item removed: " + itemStack.getHoverName().getString()).withStyle(ChatFormatting.RED));
                }
            }
        }
    }
}
