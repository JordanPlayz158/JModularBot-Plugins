package xyz.jordanplayz158.warnplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.jordanplayz158.utils.LoadJson;
import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

public class WarnCommand extends Command {
    public WarnCommand() {
        super("warn",
                null,
                "Warn a member on the server.",
                null,
                JModularBot.instance.getJda().getRoleById(JModularBot.instance.getConfig().getJson().get("hi").getAsLong()),
                "warn <user> <reason>",
                true,
                false);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();

        // Checking preconditions
        if(mentionedMembers.size() == 0) {
            channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                    .setColor(Color.RED)
                    .setTitle("Warn Error")
                    .setDescription("There is no mentioned member in the warn")
                    .build()
            ).queue();
            return;
        }

        if(args.length < 3) {
            return;
        }

        Member warnMember = mentionedMembers.get(0);

        // 1. Make a UUID for the warn
        UUID uuid = UUID.randomUUID();

        StringBuilder reason = new StringBuilder(args[2]);

        for(int i = 3; i < args.length; i++) {
            reason.append(" ").append(args[i]);
        }

        // 2. Grab the data in data/warns then save the UUID to the data/warns.json file
        JsonObject json = LoadJson.linkedTreeMap(WarnPlugin.instance.getWarnsFile());

        String warnMemberId = warnMember.getId();

        if(!json.has(warnMemberId)) {
            json.add(warnMemberId, new JsonObject());
        }

        JsonObject userWarns = json.getAsJsonObject(warnMemberId);
        String uuidString = uuid.toString();
        userWarns.addProperty(uuidString, reason.toString());

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Writer writer = Files.newBufferedWriter(WarnPlugin.instance.getWarnsFile().toPath());

            gson.toJson(json, writer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Send warn message
        channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                .setColor(Color.GREEN)
                .setTitle("Warn Successful")
                .setDescription(warnMember.getAsMention() + " has been warned")
                .addField("ID", uuidString, false)
                .build()
        ).queue();

        // 4. Send warn log message
        channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                .setColor(Color.YELLOW)
                .setTitle("Log | Warn")
                .setDescription(MessageUtils.boldNameAndTag(warnMember.getUser()) + " was warned by " + MessageUtils.boldNameAndTag(author) + " for " + reason)
                .build()).queue();
    }
}
