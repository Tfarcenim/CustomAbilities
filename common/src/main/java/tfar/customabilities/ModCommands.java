package tfar.customabilities;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.platform.Services;

import javax.annotation.Nullable;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal(CustomAbilities.MOD_ID)
                .then(Commands.literal("clear").executes(ModCommands::clearAbility))
                .then(Commands.argument("name",StringArgumentType.string())
                        .suggests(ALL_ABILITIES).executes(ModCommands::activateAbility))
                .then(Commands.literal("get").executes(ModCommands::getAbility))
        );
    }

    private static int activateAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
        try {
            String s = StringArgumentType.getString(context,"name");
            NewAbility newAbility = Abilities.ABILITIES_BY_NAME.get(s);
            NewAbility original = Services.PLATFORM.getAbility(serverPlayer);
            Services.PLATFORM.setAbility(serverPlayer,newAbility);
            onChange(serverPlayer,original,newAbility);
        } catch (IllegalArgumentException e) {
            context.getSource().sendFailure(Component.literal("Something went wrong: "+e));
            return 0;
        }
        return 1;
    }

    private static int getAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
        NewAbility ability = Services.PLATFORM.getAbility(serverPlayer);
        if (ability != null) {
            context.getSource().sendSystemMessage(Component.literal("You have the "+ability.getName()+" ability"));
        } else {
            context.getSource().sendSystemMessage(Component.literal("You have no ability enabled"));
        }
        return 1;
    }

    protected static final SuggestionProvider<CommandSourceStack> ALL_ABILITIES = (commandContext, suggestionsBuilder) ->
            SharedSuggestionProvider.suggest(Abilities.ABILITIES_BY_NAME.keySet(),suggestionsBuilder);
    private static int clearAbility(CommandContext<CommandSourceStack>context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
        NewAbility original = Services.PLATFORM.getAbility(serverPlayer);
        Services.PLATFORM.setAbility(serverPlayer,null);
        onChange(serverPlayer,original,null);
        return 1;
    }

    private static void onChange(ServerPlayer player, @Nullable NewAbility original, @Nullable NewAbility newA) {
        if (original != null) {
            original.onRemove(player);
        }
        if (newA != null) {
            newA.onGive(player);
        }
        CustomAbilities.LOG.info("{} removed {} ability, got {} ability",player,original,newA);
    }
}
