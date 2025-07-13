package net.swimmingtuna.pathtodivinity.mixin.Arphex;

import net.arphex.item.ChitinArmourTier2Item;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.arphex.item.ChitinArmourTier2Item$1")
public class ChitinArmorTier2Mixin {
    @Inject(method = "getDefenseForType", at = @At("HEAD"), cancellable = true)
    private void modifyDefenseForType(ArmorItem.Type type, CallbackInfoReturnable<Integer> cir) {
        int[] newValues = {3, 8, 6, 3};
        cir.setReturnValue(newValues[type.getSlot().getIndex()]);
    }
}