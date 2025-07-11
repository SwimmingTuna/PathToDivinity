package net.swimmingtuna.pathtodivinity;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class HealthValidateCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("validatehealth")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(context -> {
                            ServerPlayer player = EntityArgument.getPlayer(context, "player");
                            validatePlayerHealth(player, context.getSource());
                            return 1;
                        })
                )
                .executes(context -> {
                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                        validatePlayerHealth(player, context.getSource());
                        return 1;
                    } else {
                        context.getSource().sendFailure(Component.literal("Only players can use this command without specifying a target"));
                        return 0;
                    }
                })
        );
    }

    private static void validatePlayerHealth(ServerPlayer player, CommandSourceStack source) {
        float originalHealth = player.getHealth();
        if (Float.isNaN(originalHealth) || originalHealth < 0.0F) {
            player.setHealth(0.0F);
            source.sendSuccess(() -> Component.literal(
                    String.format("Fixed invalid health for %s (was: %s, now: 0.0)",
                            player.getName().getString(),
                            Float.isNaN(originalHealth) ? "NaN" : String.valueOf(originalHealth))
            ), true);
        } else {
            source.sendSuccess(() -> Component.literal(
                    String.format("%s's health is valid: %.1f",
                            player.getName().getString(),
                            originalHealth)
            ), false);
        }
    }
}