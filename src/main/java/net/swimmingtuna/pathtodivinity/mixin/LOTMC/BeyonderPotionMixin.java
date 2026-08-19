package net.swimmingtuna.pathtodivinity.mixin.LOTMC;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.swimmingtuna.lotm.item.BeyonderPotions.BeyonderPotion;
import net.swimmingtuna.pathtodivinity.SequenceLockData;
import net.swimmingtuna.pathtodivinity.profile.ProfileManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Gates drinking a Beyonder potion on the server's own rules: the sequence lock set by {@code /sequencelock},
 * and — when the profile system is on — picking a profile plus the Safemode sequence cap.
 *
 * <p>Injecting at HEAD means we bail out before LOTM starts the advance and before {@code stack.shrink(1)},
 * so a refused potion stays in the player's inventory. This mirrors LOTM's own
 * {@code corpseCollectorReincarnationSequenceAdder} guard at the top of the same method.
 *
 * <p>{@code BeyonderPotion.use} is the only route a player has into {@code AdvancingSequenceLayer}, which makes
 * it the single choke point for every advancement rule.
 */
@Mixin(BeyonderPotion.class)
public class BeyonderPotionMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void ptd$blockLockedSequences(Level level, Player player, InteractionHand hand,
                                          CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (level.isClientSide()) {
            return;
        }
        ItemStack stack = player.getItemInHand(hand);
        int potionSequence = BeyonderPotion.getSequence(stack);

        int lockedSequence = SequenceLockData.blockingLockFor(player, potionSequence);
        if (lockedSequence != SequenceLockData.NO_LOCK) {
            player.sendSystemMessage(Component.literal(
                            "Advancement past Sequence " + lockedSequence + " is currently locked on this server.")
                    .withStyle(ChatFormatting.RED));
            cir.setReturnValue(InteractionResultHolder.fail(stack));
            return;
        }

        if (!ProfileManager.isEnabled() || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        // A player with no profile yet has to choose before they can become a Beyonder at all. Refusing here
        // keeps the potion, so they can click a button and drink again.
        if (ProfileManager.needsToChooseProfile(serverPlayer)) {
            ProfileManager.sendChoicePrompt(serverPlayer);
            cir.setReturnValue(InteractionResultHolder.fail(stack));
            return;
        }

        if (ProfileManager.blocksSequence(player, potionSequence)) {
            Integer cap = ProfileManager.getSequenceCap(player);
            player.sendSystemMessage(Component.literal(
                            "Your Safemode profile cannot advance past Sequence " + cap
                                    + ". Switch to your Normal profile to go further.")
                    .withStyle(ChatFormatting.RED));
            cir.setReturnValue(InteractionResultHolder.fail(stack));
        }
    }
}
