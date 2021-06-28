package xyz.jordanplayz158.muteplugin;

import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;

import java.awt.Color;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MuteCommand extends Command {
    public MuteCommand() {
        super("mute",
                null,
                "Mute a member of the discord (default 30 minute mute)",
                null,
                JModularBot.instance.getJda().getRoleById(JModularBot.instance.getConfig().getJson().get("hi").getAsLong()),
                 "mute <user> <time>",
                true,
                false);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        Guild guild = event.getGuild();
        Member muteMember = Objects.requireNonNull(guild.getMember(MessageUtils.extractMention(args[1])));
        JModularBot instance = JModularBot.instance;
        Role muteRole = Objects.requireNonNull(instance.getJda().getRoleById(instance.getConfig().getJson().get("hi").getAsLong()));

        if(args.length < 3) {
            return;
        }

        String delay = args[2];

        guild.addRoleToMember(muteMember, muteRole).queue(success -> event.getChannel().sendMessageEmbeds(JModularBot.getTemplate(event.getAuthor())
                .setColor(Color.GREEN)
                .setTitle("Mute Successful")
                .setDescription(MessageUtils.nameAndTag(muteMember.getUser()) + " has been muted for " + delay)
                .build()).queue(success2 -> event.getChannel().sendMessageEmbeds(JModularBot.getTemplate(event.getAuthor())
                        .setColor(Color.YELLOW)
                        .setTitle("Log | Mute")
                        .setDescription(MessageUtils.boldNameAndTag(Objects.requireNonNull(event.getMember()).getUser()) + " was muted by " + MessageUtils.boldNameAndTag(event.getAuthor()) + " for " + delay)
                        .build()).queue()));

        Executors.newScheduledThreadPool(1).schedule(() -> guild.removeRoleFromMember(muteMember, muteRole).queue(),
                Long.parseLong(delay.substring(0, delay.length() - 1)),
                getTimeUnit(delay));

    }

    public TimeUnit getTimeUnit(String time) {
        return switch (time.substring(time.length() - 1).toLowerCase()) {
            case "s" -> TimeUnit.SECONDS;
            case "m" -> TimeUnit.MINUTES;
            case "h" -> TimeUnit.HOURS;
            case "d" -> TimeUnit.DAYS;
            default -> null;
        };
    }
}