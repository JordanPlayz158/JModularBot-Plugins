package xyz.jordanplayz158.banplugin;

import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;

import java.awt.Color;

public class BanCommand extends Command {
    public BanCommand() {
        super("ban",
                null,
                "Ban a member from the guild!",
                Permission.BAN_MEMBERS,
                null,
                "ban <user> <reason>",
                true,
                true);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length < 3) {
            return;
        }

        TextChannel channel = event.getTextChannel();
        MessageEmbed invalidUser = JModularBot.getTemplate(event.getAuthor())
                .setColor(Color.RED)
                .setTitle("Ban Failed")
                .setDescription("The user does not exist")
                .build();

        if(event.getMessage().getMentionedMembers().size() < 1) {
            channel.sendMessageEmbeds(invalidUser).queue();
            return;
        }

        Member member = event.getMessage().getMentionedMembers().get(0);
        StringBuilder reason = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        try {
            event.getGuild().ban(member, 0).reason(reason.toString()).queue();
        } catch (Exception e) {
            channel.sendMessageEmbeds(invalidUser).queue();
            return;
        }

        channel.sendMessageEmbeds(JModularBot.getTemplate(event.getAuthor())
                .setColor(Color.GREEN)
                .setTitle("Ban Successful")
                .setDescription(MessageUtils.boldNameAndTag(member.getUser()) + " was banned")
                .build()).queue();

        event.getChannel().sendMessageEmbeds(JModularBot.getTemplate(event.getAuthor())
                .setColor(Color.YELLOW)
                .setTitle("Log | Ban")
                .setDescription(MessageUtils.boldNameAndTag(member.getUser()) + " was banned by " + MessageUtils.boldNameAndTag(event.getAuthor()) + " for \"" + reason + "\"")
                .build()).queue();
    }
}