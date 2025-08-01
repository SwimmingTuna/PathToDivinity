package net.swimmingtuna.pathtodivinity.mixin.LOTMC;

import net.mcreator.terramity.init.TerramityModEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.swimmingtuna.lotm.util.BeyonderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeyonderUtil.class, remap = false)
public class UltraSnifferAgeMixin {

    @ModifyVariable(
            method = "ageHandlerTick",
            at = @At(value = "LOAD", ordinal = 0),
            name = "maxAge"
    )
    private static int modifyMaxAgeForUltraSniffer(int maxAge, LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getType() == TerramityModEntities.ULTRA_SNIFFER.get()) {
            return 6000;
        }
        return maxAge;
    }
}