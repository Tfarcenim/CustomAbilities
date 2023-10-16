package tfar.customabilities;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal(CustomAbilities.MOD_ID).then(Commands.literal("clear").executes(ModCommands::clearAbility))
                .then(Commands.argument("name",StringArgumentType.string()).suggests(VALID_ABILITIES)).executes(ModCommands::activateAbility));
    }

    private static int activateAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
        try {
            String s = StringArgumentType.getString(context,"name");
            Ability ability = Ability.valueOf(s);
            ((PlayerDuck)serverPlayer).setAbility(ability);
        } catch (IllegalArgumentException e) {
            return 0;
        }
        return 1;
    }

    private static final SuggestionProvider<CommandSourceStack> VALID_ABILITIES =
            (context, builder) -> SharedSuggestionProvider.suggest(Arrays.stream(Ability.values()).map(Enum::name).collect(Collectors.toList()), builder);
    private static int clearAbility(CommandContext<CommandSourceStack>context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
        ((PlayerDuck)serverPlayer).setAbility(null);
        return 1;
    }
}
