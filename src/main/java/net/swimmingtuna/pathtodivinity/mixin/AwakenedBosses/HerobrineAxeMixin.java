package net.swimmingtuna.pathtodivinity.mixin.AwakenedBosses;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.cursedwarrior.awakenedbosses.item.HerobrineAxeItem$1")
public class HerobrineAxeMixin {

    @Inject(method = "getAttackDamageBonus", at = @At("HEAD"), cancellable = true)
    private void modifyDamage(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(15.0f);
    }
}
