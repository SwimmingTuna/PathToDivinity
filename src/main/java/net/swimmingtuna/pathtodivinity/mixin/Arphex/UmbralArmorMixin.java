package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Umbral Armor Mixin (5 10 8 5)
@Mixin(targets = "net.arphex.item.UmbralItem$1")
public class UmbralArmorMixin {
    @Inject(method = "getDefenseForType", at = @At("HEAD"), cancellable = true)
    private void modifyDefenseForType(ArmorItem.Type type, CallbackInfoReturnable<Integer> cir) {
        int[] newValues = {5, 8, 10, 5};
        cir.setReturnValue(newValues[type.getSlot().getIndex()]);
    }
    @Inject(method = "getToughness", at = @At("HEAD"), cancellable = true)
    private void modifyToughnessForType(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(4.0f);
    }
}