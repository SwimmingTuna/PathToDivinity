package net.swimmingtuna.pathtodivinity.mixin.ObscureAPI;


import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ObscureAPIAttributes.class, remap = false)
public class ObscureAPIAttributesMixin {

    @Inject(method = "criticalHitAndMagicResistanceEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void disableEvent(LivingHurtEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}
