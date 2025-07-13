package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.item.ValamonAxeItem;
import com.curseforge.macabre.item.ValamonSickleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ValamonAxeItem.class, remap = false)
public class SweepingWaraxeMixin {

    @ModifyConstant(
            method = "getDefaultAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;",
            constant = @Constant(doubleValue = 16.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 12.0;
    }
}
