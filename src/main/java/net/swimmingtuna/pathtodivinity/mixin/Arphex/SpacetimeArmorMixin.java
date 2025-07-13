package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.arphex.item.SpacetimeItem$1")
public class SpacetimeArmorMixin {
    @Inject(method = "getDefenseForType", at = @At("HEAD"), cancellable = true)
    private void modifyDefenseForType(ArmorItem.Type type, CallbackInfoReturnable<Integer> cir) {
        int[] newValues = {5, 9, 11, 6};
        cir.setReturnValue(newValues[type.getSlot().getIndex()]);
    }
    @Inject(method = "getToughness", at = @At("HEAD"), cancellable = true)
    private void modifyToughnessForType(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(4.0f);
    }
}
