package xyz.jordanplayz158.warnplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WarnCommand extends Command {
    public WarnCommand() {
        super("warn",
                null,
                "Warn a member on the server.",
                null,
                JModularBot.instance.getJda().getRoleById(WarnPlugin.instance.getConfig().getStaffRole()),
                "warn <user> <reason>",
                true,
                false);

        try {
            File warnsFile = WarnPlugin.instance.getWarnsFile();

            if(Files.size(warnsFile.toPath()) != 0) {
                Reader reader = Files.newBufferedReader(warnsFile.toPath());

                final Gson gson = new GsonBuilder().setPrettyPrinting().create();
                WarnPlugin.instance.setWarns(gson.fromJson(reader, Map.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(MessageReceivedEvent event, String[] args) {
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if(args.length < 3 || mentionedMembers.size() == 0) {
            return false;
        }

        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();

        Member warnMember = mentionedMembers.get(0);

        // 1. Make a UUID for the warn
        String uuid = UUID.randomUUID().toString();
        String warnMemberId = warnMember.getId();

        String reason = getReason(args);

        Map<String, List<Warn>> warns = WarnPlugin.instance.getWarns();

        warns.putIfAbsent(warnMemberId, new ArrayList<>());
        warns.get(warnMemberId).add(new Warn(uuid, reason));

        // 3. Send warn message
        channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                .setColor(Color.GREEN)
                .setTitle("Warn Successful")
                .setDescription(warnMember.getAsMention() + " has been warned")
                .addField("ID", uuid, false)
                .build()
        ).queue();

        // 4. Send warn log message
        channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                .setColor(Color.YELLOW)
                .setTitle("Log | Warn")
                .setDescription(MessageUtils.boldNameAndTag(warnMember.getUser()) + " was warned by " + MessageUtils.boldNameAndTag(author) + " for " + reason)
                .build()).queue();

        return true;
    }

    private static String getReason(String[] args) {
        StringBuilder reason = new StringBuilder(args[2]);

        for(int i = 3; i < args.length; i++) {
            reason.append(" ").append(args[i]);
        }

        return reason.toString();
    }
}
