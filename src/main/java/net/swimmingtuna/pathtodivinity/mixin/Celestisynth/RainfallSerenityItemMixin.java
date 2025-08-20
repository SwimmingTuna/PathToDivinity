package net.swimmingtuna.pathtodivinity.mixin.Celestisynth;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.thecelestialworkshop.celestisynth.common.compat.apotheosis.CSCompatAP;
import org.thecelestialworkshop.celestisynth.common.entity.projectile.RainfallArrow;
import org.thecelestialworkshop.celestisynth.common.item.weapons.RainfallSerenityItem;
import org.thecelestialworkshop.celestisynth.manager.CSIntegrationManager;

@Mixin(value = RainfallSerenityItem.class, remap = false)
public class RainfallSerenityItemMixin {

    /**
     * @author SwimmingTuna
     * @reason Reduce damage to 40% of original
     */
    @Overwrite
    public AbstractArrow customArrow(AbstractArrow arrow) {
        RainfallArrow rainfallArrow = new RainfallArrow(arrow);
        rainfallArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        if (arrow.getOwner() != null) {
            rainfallArrow.setOrigin(arrow.getOwner().position());
        }
        rainfallArrow.setPierceLevel((byte)3);
        rainfallArrow.setImbueQuasar(true);
        rainfallArrow.setStrong(true);

        if (CSIntegrationManager.checkApothicAttributes()) {
            Entity var4 = arrow.getOwner();
            if (var4 instanceof LivingEntity owner) {
                CSCompatAP.installApothRainfallDamage(rainfallArrow, owner);
            }
        }

        rainfallArrow.setBaseDamage(rainfallArrow.getBaseDamage() * 0.15);

        return rainfallArrow;
    }
}