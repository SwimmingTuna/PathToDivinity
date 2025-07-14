package net.swimmingtuna.pathtodivinity.mixin.Cataclysm;

import com.github.L_Ender.cataclysm.entity.projectile.Lightning_Spear_Entity;
import com.github.L_Ender.cataclysm.items.Astrape;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = Astrape.class, remap = false)
public class AstrapeMixin {

    @ModifyConstant(
            method = "<init>(Lnet/minecraft/world/item/Item$Properties;)V",
            constant = @Constant(doubleValue = 9.5)
    )
    private double modifyAttackDamage(double damage) {
        return damage + 25.0;
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Lightning_Spear_Entity;<init>(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/Level;F)V"),
            index = 3,
            remap = false
    )
    private float multiplyLightningDamage(float damage) {
        return damage * 7.0F;
    }

    @ModifyArg(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Lightning_Spear_Entity;setAreaDamage(F)V"),
            index = 0
    )
    private float multiplyAreaDamage(float areaDamage) {
        return areaDamage * 7.0F;
    }
}