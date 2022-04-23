package xyz.jordanplayz158.messagepreserverplugin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        User messageSender = event.getAuthor();

        if(MessagePreserverPlugin.userIds.contains(messageSender.getIdLong())) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setTitle(String.format("%s#%s (%s)", messageSender.getName(), messageSender.getDiscriminator(), messageSender.getIdLong()));
            embed.addField("Message", event.getMessage().getContentRaw(), false);
            embed.addField("Time", "<t:" + event.getMessage().getTimeCreated().toEpochSecond() + ">" , false);

            event.getChannel().sendMessageEmbeds(embed.build()).queue();
        }
    }
}
