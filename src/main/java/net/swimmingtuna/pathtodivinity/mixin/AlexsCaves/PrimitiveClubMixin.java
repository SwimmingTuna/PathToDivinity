package net.swimmingtuna.pathtodivinity.mixin.AlexsCaves;

import com.github.alexmodguy.alexscaves.server.item.PrimitiveClubItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = PrimitiveClubItem.class, remap = false)
public class PrimitiveClubMixin {

    @ModifyConstant(
            method = "getStatsForEnchantmentLevel",
            constant = @Constant(doubleValue = 8.0)
    )
    private double modifyAttackDamage(double original) {
        return 11.0;
    }
}
