package xyz.jordanplayz158.unwarnplugin;

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
import java.util.Objects;

public class UnwarnCommand extends Command {
    public UnwarnCommand() {
        super("unwarn",
                List.of("remwarn", "delwarn"),
                "Remove a warning from a member on the server.",
                null,
                JModularBot.instance.getJda().getRoleById(JModularBot.instance.getConfig().getJson().get("hi").getAsLong()),
                "unwarn <uuid> <reason>",
                true,
                false);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        // Checking preconditions
        if(args.length < 3) {
            return;
        }

        StringBuilder reason = new StringBuilder(args[2]);

        for(int i = 3; i < args.length; i++) {
            reason.append(" ").append(args[i]);
        }

        // 2. Grab the data in data/warns then save the UUID to the data/warns.json file
        JsonObject json = LoadJson.linkedTreeMap(UnwarnPlugin.instance.getWarnsFile());
        String userId = getUserId(json, args[1]);

        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();

        if(userId == null) {
            channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                    .setColor(Color.RED)
                    .setTitle("Unwarn Error")
                    .setDescription("There is no warn with that uuid!")
                    .build()
            ).queue();
            return;
        }

        Member unwarnMember = Objects.requireNonNull(event.getGuild().getMemberById(userId));
        json.getAsJsonObject(userId).remove(args[1]);

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Writer writer = Files.newBufferedWriter(UnwarnPlugin.instance.getWarnsFile().toPath());

            gson.toJson(json, writer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Send warn message
        channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                .setColor(Color.GREEN)
                .setTitle("Unwarn Successful")
                .setDescription(unwarnMember.getAsMention() + " has been unwarned (The warn has been deleted)")
                .addField("Deleted ID", args[1], false)
                .build()
        ).queue();

        // 4. Send warn log message
        channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                .setColor(Color.YELLOW)
                .setTitle("Log | Unwarn")
                .setDescription(MessageUtils.boldNameAndTag(unwarnMember.getUser()) + " was unwarned by " + MessageUtils.boldNameAndTag(author) + " for " + reason)
                .build()).queue();
    }

    private String getUserId(JsonObject json, String uuid) {
        for(String key : json.keySet()) {
            if(json.getAsJsonObject(key).has(uuid)) {
                return key;
            }
        }

        return null;
    }
}
