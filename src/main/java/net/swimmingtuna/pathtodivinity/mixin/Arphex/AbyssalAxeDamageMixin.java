package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.item.AbyssalAxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbyssalAxeItem.class)
public class AbyssalAxeDamageMixin {

    @ModifyArg(
            method = "<init>()V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/AxeItem;<init>(Lnet/minecraft/world/item/Tier;FFLnet/minecraft/world/item/Item$Properties;)V"),
            index = 1
    )
    private static float modifyAttackDamage(float attackDamage) {
        return 16.0F;
    }
}