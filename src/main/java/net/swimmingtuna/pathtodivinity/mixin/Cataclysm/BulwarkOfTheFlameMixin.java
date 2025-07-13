package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Bulwark_of_the_flame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Bulwark_of_the_flame.class, remap = false)
public class BulwarkOfTheFlameMixin {

    @ModifyConstant(
            method = "releaseUsing(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V",
            constant = @Constant(floatValue = 0.6F)
    )
    private float modifyDamage(float damage) {
        return damage * 3.5F;
    }
}