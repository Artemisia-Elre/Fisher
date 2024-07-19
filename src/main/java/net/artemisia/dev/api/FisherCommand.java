package net.artemisia.dev.api;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.artemisia.dev.api.configuration.FisherConfig;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;

public class FisherCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> command = dispatcher.register(
                Commands.literal("fisher")
                        .requires((player) -> player.hasPermission(2))
                        .then(Commands.literal("reload")
                                .executes(FisherCommand::reload)
                        ).then(Commands.literal("get")
                                .executes(FisherCommand::get)
                        )
        );


    }

    private static int get(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {
        commandSourceStackCommandContext.getSource().sendSystemMessage(Component.literal("Loots:" + FisherRegistries.getLootIds()));
        commandSourceStackCommandContext.getSource().sendSystemMessage(Component.literal("Table:" + FisherRegistries.getTableIds()));

        return 0;
    }

    private static int reload(CommandContext<CommandSourceStack> context){
        FisherConfig.init();
        context.getSource().sendSystemMessage(Component.translatable("message.reload.susses"));
        return 0;
    }

}
