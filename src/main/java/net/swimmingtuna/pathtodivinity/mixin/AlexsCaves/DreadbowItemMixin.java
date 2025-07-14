package net.swimmingtuna.pathtodivinity.mixin.AlexsCaves;

import com.github.alexmodguy.alexscaves.server.entity.item.DarkArrowEntity;
import com.github.alexmodguy.alexscaves.server.item.DreadbowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = DreadbowItem.class, remap = true)
public class DreadbowItemMixin {

    @Redirect(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/entity/item/DarkArrowEntity;setShadowArrowDamage(F)V"))
    private void modifyShadowArrowDamage(DarkArrowEntity darkArrowEntity, float damage, ItemStack itemStack, Level level, LivingEntity livingEntity, int i1) {
        boolean isPrecise = damage == 2.0F;
        float newDamage = isPrecise ? 5.0F : 7.5F;
        darkArrowEntity.setShadowArrowDamage(newDamage);
    }

    @Redirect(method = "onUseTick", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/entity/item/DarkArrowEntity;setShadowArrowDamage(F)V"))
    private void modifyShadowArrowDamageOnUseTick(DarkArrowEntity darkArrowEntity, float damage) {
        darkArrowEntity.setShadowArrowDamage(5.0F);
    }
}