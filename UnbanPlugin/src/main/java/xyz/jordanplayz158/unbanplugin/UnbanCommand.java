package xyz.jordanplayz158.unbanplugin;

import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;

import java.awt.Color;

public class UnbanCommand extends Command {
    public UnbanCommand() {
        super("unban",
                null,
                "Unban a member from the guild!",
                null,
                JModularBot.instance.getJda().getRoleById(JModularBot.instance.getConfig().getJson().get("hi").getAsLong()),
                "unban <user> [reason]",
                true,
                false);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        StringBuilder reason = null;

        if(args.length > 2) {
            reason = new StringBuilder();

            for(int i = 2; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
        }

        TextChannel channel = event.getTextChannel();
        User user = MessageUtils.extractMention(args[1]);
        User author = event.getAuthor();
        StringBuilder finalReason = reason;

        event.getGuild().unban(user).queue(success -> {

            channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                    .setColor(Color.GREEN)
                    .setTitle("Unban Successful")
                    .setDescription("The user with the id of " + user.getId() + " has been unbanned")
                    .build())
                    .queue();

            EmbedBuilder embed = JModularBot.getTemplate(author)
                    .setColor(Color.YELLOW)
                    .setTitle("Log | Unban");

            String boldUserNameAndTag = MessageUtils.boldNameAndTag(user);
            String boldAuthorNameAndTag = MessageUtils.boldNameAndTag(author);

            embed.setDescription(boldUserNameAndTag + " was unbanned by " + boldAuthorNameAndTag);

            if(finalReason == null) {
                channel.sendMessageEmbeds(embed.build()).queue();
            } else {
                channel.sendMessageEmbeds(embed.appendDescription(" for " + finalReason).build()).queue();
            }
        }, error -> {
            int errorCode = Integer.parseInt(error.getMessage().substring(0, error.getMessage().indexOf(":")));
            if(errorCode == 10026)
                channel.sendMessageEmbeds(JModularBot.getTemplate(author).setColor(Color.RED).setTitle("Unban Unsuccessful").setDescription("The user is not banned!").build()).queue();
        });
    }
}