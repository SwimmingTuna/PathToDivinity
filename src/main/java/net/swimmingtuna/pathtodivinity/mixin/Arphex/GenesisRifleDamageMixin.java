package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.procedures.GenesisRifleReleasedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GenesisRifleReleasedProcedure.class, remap = false)
public class GenesisRifleDamageMixin {

    @ModifyConstant(
            method = "execute",
            constant = @Constant(floatValue = 5.0F)
    )
    private static float modifyDamage(float damage) {
        return damage * 32.0F; // Multiply damage by 40, making it 200.0F
    }
}
