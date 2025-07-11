package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;

import com.aqutheseal.celestisynth.common.item.weapons.BreezebreakerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BreezebreakerItem.class, remap = false)
public class BreezebreakerMixin {

    @Inject(method = "onPlayerHurt(Lnet/minecraftforge/event/entity/living/LivingHurtEvent;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private void cancelPlayerHurt(LivingHurtEvent event, ItemStack stack, CallbackInfo ci) {
        ci.cancel();
        if (event.getSource() == event.getEntity().damageSources().fall()) {
            event.setCanceled(true);
        }
    }
}