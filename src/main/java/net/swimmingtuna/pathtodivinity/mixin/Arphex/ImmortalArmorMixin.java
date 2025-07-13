package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.item.ImmortalItem;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.arphex.item.ImmortalItem$1")
public class ImmortalArmorMixin {

    @Inject(method = "getDefenseForType", at = @At("HEAD"), cancellable = true)
    private void modifyDefenseForType(ArmorItem.Type type, CallbackInfoReturnable<Integer> cir) {
        int[] newValues = {7, 14, 12, 7};
        cir.setReturnValue(newValues[type.getSlot().getIndex()]);
    }
}