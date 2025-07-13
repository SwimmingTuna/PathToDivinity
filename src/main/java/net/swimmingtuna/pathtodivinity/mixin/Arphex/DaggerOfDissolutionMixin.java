package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.item.DaggerOfDissolutionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DaggerOfDissolutionItem.class)
public class DaggerOfDissolutionMixin {

    @ModifyConstant(
            method = "getDefaultAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;",
            constant = @Constant(doubleValue = 2.0)
    )
    private double modifyAttackDamage(double damage) {
        return 5.5; // Change from 7.0 to 17.0 (7.0 + 1.0 base = 8.0 total, 17.0 + 1.0 base = 18.0 total)
    }
}
