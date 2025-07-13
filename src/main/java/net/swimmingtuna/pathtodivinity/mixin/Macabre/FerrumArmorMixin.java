package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.curseforge.macabre.item.FerrumArmorItem$1")
public class FerrumArmorMixin {

    @Inject(method = "getToughness", at = @At("HEAD"), cancellable = true)
    private void modifyToughnessForType(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1.0f);
    }
}
