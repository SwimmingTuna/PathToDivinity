package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.item.AbyssAtomiserItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbyssAtomiserItem.class)
public class AbyssAtomiserDamageMixin {

    @ModifyConstant(
            method = "getDefaultAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;",
            constant = @Constant(doubleValue = 15.0)
    )
    private double modifyAttackDamage(double damage) {
        return 20.0;
    }
}
