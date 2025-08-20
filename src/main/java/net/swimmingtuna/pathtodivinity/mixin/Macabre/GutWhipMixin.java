package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.item.ValamonGutWhipItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ValamonGutWhipItem.class, remap = true)
public class GutWhipMixin {

    @ModifyConstant(
            method = "getDefaultAttributeModifiers",
            constant = @Constant(doubleValue = 11.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 12.0;
    }
}
