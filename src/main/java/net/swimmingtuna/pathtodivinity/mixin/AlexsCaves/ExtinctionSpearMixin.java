package net.swimmingtuna.pathtodivinity.mixin.AlexsCaves;

import com.github.alexmodguy.alexscaves.server.item.ExtinctionSpearItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = ExtinctionSpearItem.class, remap = false)
public class ExtinctionSpearMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/item/SpearItem;<init>(Lnet/minecraft/world/item/Item$Properties;D)V"), index = 1)
    private static double modifyDamage(double damage) {
        return 27.0;
    }
}