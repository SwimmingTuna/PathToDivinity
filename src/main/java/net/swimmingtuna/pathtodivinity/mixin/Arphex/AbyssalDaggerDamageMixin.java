package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.item.AbyssalDaggerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbyssalDaggerItem.class)
public class AbyssalDaggerDamageMixin {

    @ModifyConstant(
            method = "getDefaultAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;",
            constant = @Constant(doubleValue = 7.0)
    )
    private double modifyAttackDamage(double damage) {
        return 12.0;
    }
}
