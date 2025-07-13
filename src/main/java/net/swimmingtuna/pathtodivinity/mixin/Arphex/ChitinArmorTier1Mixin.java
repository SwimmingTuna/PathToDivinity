package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.arphex.item.ChitinArmourItem$1")
public class ChitinArmorTier1Mixin {
    @Inject(method = "getDefenseForType", at = @At("HEAD"), cancellable = true)
    private void modifyDefenseForType(ArmorItem.Type type, CallbackInfoReturnable<Integer> cir) {
        int[] newValues = {3, 5, 6, 2};
        cir.setReturnValue(newValues[type.getSlot().getIndex()]);
    }
    @Inject(method = "getToughness", at = @At("HEAD"), cancellable = true)
    private void modifyToughnessForType(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1.0f);
    }
}
