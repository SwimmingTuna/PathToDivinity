package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.items.Gauntlet_of_Bulwark;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Gauntlet_of_Bulwark.class, remap = false)
public class GauntletOfBulwarkMixin { //ATTRIBUTE

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 10.0)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 20.0;
    }

    @ModifyConstant(
            method = "onUseTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;I)V",
            constant = @Constant(intValue = 40)
    )
    private int modifyBlazingBrandDuration(int duration) {
        return 100;
    }

    @ModifyConstant(
            method = "releaseUsing(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V",
            constant = @Constant(floatValue = 1.2F)
    )
    private float modifyChargeDamage(float damage) {
        return damage * 6.0F; // 6x charge damage: 1.2F * 6 = 7.2F
    }
}
