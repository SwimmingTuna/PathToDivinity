package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;


import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.thecelestialworkshop.celestisynth.common.registry.CSItemTiers;
import org.thecelestialworkshop.celestisynth.common.registry.CSItems;
import org.thecelestialworkshop.celestisynth.common.registry.CSTags;

import java.util.List;

@Mixin(value = CSItemTiers.class, remap = false)
public class CelestisynthItemTiersMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/TierSortingRegistry;registerTier(Lnet/minecraft/world/item/Tier;Lnet/minecraft/resources/ResourceLocation;Ljava/util/List;Ljava/util/List;)Lnet/minecraft/world/item/Tier;"))
    private static Tier redirectRegisterTier(Tier tier, net.minecraft.resources.ResourceLocation name, List<Object> after, List<Object> before) {
        ForgeTier modifiedTier = new ForgeTier(5, 2550, 9.0F, 12.0F, 15, CSTags.Blocks.NEEDS_CELESTIAL_TOOL, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) CSItems.CELESTIAL_CORE_HEATED.get()});
        });
        return TierSortingRegistry.registerTier(modifiedTier, name, after, before);
    }
}