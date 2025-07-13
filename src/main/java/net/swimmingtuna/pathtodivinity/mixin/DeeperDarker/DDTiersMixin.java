package net.swimmingtuna.pathtodivinity.mixin.DeeperDarker;

import com.kyanite.deeperdarker.util.DDTiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = DDTiers.class)
public class DDTiersMixin {

    @ModifyArg(
            method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lcom/kyanite/deeperdarker/util/DDTiers;<init>(Ljava/lang/String;IIIFFILnet/minecraft/world/level/ItemLike;)V"),
            index = 5
    )
    private static float modifyWardenDamage(float damage) {
        return damage + 3.0F;
    }
}