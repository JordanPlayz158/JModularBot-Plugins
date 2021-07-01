package xyz.jordanplayz158.kickplugin;

import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;

import java.awt.Color;

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
    public boolean onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length < 3)
            return false;

        Member kickedMember = event.getMessage().getMentionedMembers().get(0);
        StringBuilder reason = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        User user = event.getAuthor();
        MessageChannel channel = event.getChannel();

        event.getGuild().kick(kickedMember).reason(reason.toString()).queue(success -> {
            channel.sendMessageEmbeds(JModularBot.getTemplate(user)
                    .setColor(Color.GREEN)
                    .setTitle("Kick Successful")
                    .setDescription(MarkdownUtil.bold(MessageUtils.nameAndTag(kickedMember.getUser()))
                            + " was kicked")
                    .build()).queue();

            channel.sendMessageEmbeds(JModularBot.getTemplate(user)
                    .setColor(Color.YELLOW)
                    .setTitle("Log | Kick")
                    .setDescription(MessageUtils.boldNameAndTag(kickedMember.getUser()) + " was kicked by " + MessageUtils.boldNameAndTag(user) + " for \"" + reason + "\"")
                    .build()).queue();
        }, error -> channel.sendMessageEmbeds(JModularBot.getTemplate(user)
                .setColor(Color.RED)
                .setTitle("Kick Failed")
                .setDescription("The kick didn't execute correctly, Please check console for details!")
                .build()).queue());

        return true;
    }
}
