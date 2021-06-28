package xyz.jordanplayz158.helpplugin;

import xyz.jordanplayz158.jmodularbot.commands.Command;
import xyz.jordanplayz158.jmodularbot.managers.CommandManager;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.MarkdownUtil;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help",
                null,
                "Help menu that tells you all the commands and their functions!",
                null,
                null,
                "help",
                false,
                false);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = JModularBot.getTemplate(event.getAuthor())
                .setColor(Color.YELLOW)
                .setTitle("Help");

        Member executor = Objects.requireNonNull(event.getMember());

        for(List<Command> commandList : CommandManager.getCommandMap().values()) {
            for(Command command : commandList) {
                if (permissionCheck(executor, command) && roleCheck(executor, command)) {
                    embed.appendDescription(commandName(command));
                    embed.appendDescription(" - ");
                    embed.appendDescription(command.getDescription());
                    embed.appendDescription("\n");
                    embed.appendDescription("Syntax: `");
                    embed.appendDescription(command.getSyntax());
                    embed.appendDescription("`\n\n");
                }
            }
        }

        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

    private boolean permissionCheck(Member executor, Command command) {
        return executor.hasPermission(command.getPermission()) || command.getPermission() == null;
    }

    private boolean roleCheck(Member executor, Command command) {
        return executor.getRoles().contains(command.getRole()) || command.getRole() == null;
    }

    private String commandName(Command command) {
        return MarkdownUtil.bold(MessageUtils.upperCaseFirstLetter(command.getName()));
    }
}
