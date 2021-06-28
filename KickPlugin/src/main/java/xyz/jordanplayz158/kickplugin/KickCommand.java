package xyz.jordanplayz158.kickplugin;

import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;

import java.awt.*;

public class KickCommand extends Command {
    public KickCommand() {
        super("kick",
                null,
                "Kicks a member from the guild!",
                null,
                JModularBot.instance.getJda().getRoleById(780073237743665213L),
                "kick <user> <reason>",
                true,
                true);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length < 3)
            return;

        Member kickedMember = event.getMessage().getMentionedMembers().get(0);
        StringBuilder reason = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        event.getGuild().kick(kickedMember).reason(reason.toString()).queue(success -> {
            event.getChannel().sendMessageEmbeds(JModularBot.instance.getTemplate(event.getAuthor())
                    .setColor(Color.GREEN)
                    .setTitle("Kick Successful")
                    .setDescription(MarkdownUtil.bold(MessageUtils.nameAndTag(kickedMember.getUser()))
                            + " was kicked")
                    .build()).queue();

            event.getChannel().sendMessageEmbeds(JModularBot.instance.getTemplate(event.getAuthor())
                    .setColor(Color.YELLOW)
                    .setTitle("Log | Kick")
                    .setDescription(MessageUtils.boldNameAndTag(kickedMember.getUser()) + " was kicked by " + MessageUtils.boldNameAndTag(event.getAuthor()) + " for \"" + reason + "\"")
                    .build()).queue();
        }, error -> event.getChannel().sendMessageEmbeds(JModularBot.instance.getTemplate(event.getAuthor())
                .setColor(Color.RED)
                .setTitle("Kick Failed")
                .setDescription("The kick didn't execute correctly, Please check console for details!")
                .build()).queue());
    }
}
