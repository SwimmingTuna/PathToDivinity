package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import com.curseforge.macabre.item.ValamonAxeItem;
import net.mcreator.terramity.item.UnholyLanceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = UnholyLanceItem.class, remap = true)
public class UnholyLanceMixin {

    @ModifyConstant(
            method = "getDefaultAttributeModifiers",
            constant = @Constant(doubleValue = 15.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 9.0;
    }
}
