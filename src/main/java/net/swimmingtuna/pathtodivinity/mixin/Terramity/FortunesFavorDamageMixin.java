package net.swimmingtuna.pathtodivinity.mixin.Terramity;

import net.mcreator.terramity.procedures.FortunesFavorRightclickedProcedure;
import net.mcreator.terramity.procedures.SharperRoundsDamageProcedure;
import net.mcreator.terramity.init.TerramityModEnchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FortunesFavorRightclickedProcedure.class, remap = false)
public class FortunesFavorDamageMixin {

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lnet/mcreator/terramity/procedures/SharperRoundsDamageProcedure;execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/item/ItemStack;)D"))
    private static double amplifyBulletDamage(LevelAccessor world, ItemStack itemstack) {
        double originalDamage = SharperRoundsDamageProcedure.execute(world, itemstack);
        return originalDamage * 4.0;
    }

    @SuppressWarnings("deprecation")
    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/item/ItemStack;)I"))
    private static int interceptEnchantmentCheck(Enchantment enchantment, ItemStack itemstack) {
        int originalLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack);
        if (enchantment == TerramityModEnchantments.SHARPER_ROUNDS.get()) {
            if (originalLevel == 0) {
                return 1;
            }
        }
        return originalLevel;
    }
}