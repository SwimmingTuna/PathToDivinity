package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.curseforge.macabre.item.BaalArmorItem$1")
public class BaalArmorMixin {
    @Inject(method = "getDefenseForType", at = @At("HEAD"), cancellable = true)
    private void modifyDefenseForType(ArmorItem.Type type, CallbackInfoReturnable<Integer> cir) {
        int[] newValues = {6, 9, 10, 5};
        cir.setReturnValue(newValues[type.getSlot().getIndex()]);
    }

    @Inject(method = "getToughness", at = @At("HEAD"), cancellable = true)
    private void modifyToughnessForType(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(4.0f);
    }
}
