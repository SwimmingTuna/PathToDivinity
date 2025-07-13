package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.item.UmbralItem;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Umbral Armor Mixin (5 10 8 5)
@Mixin(targets = "net.arphex.item.UmbralItem$1")
public class UmbralArmorMixin {
    @Inject(method = "getDefenseForType", at = @At("HEAD"), cancellable = true)
    private void modifyDefenseForType(ArmorItem.Type type, CallbackInfoReturnable<Integer> cir) {
        int[] newValues = {5, 10, 8, 5};
        cir.setReturnValue(newValues[type.getSlot().getIndex()]);
    }
}