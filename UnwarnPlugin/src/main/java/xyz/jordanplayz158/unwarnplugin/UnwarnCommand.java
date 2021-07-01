package xyz.jordanplayz158.unwarnplugin;

import me.jordanplayz158.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import xyz.jordanplayz158.jmodularbot.JModularBot;
import xyz.jordanplayz158.jmodularbot.commands.Command;
import xyz.jordanplayz158.warnplugin.Warn;
import xyz.jordanplayz158.warnplugin.WarnPlugin;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public class UnwarnCommand extends Command {
    public UnwarnCommand() {
        super("unwarn",
                List.of("remwarn", "delwarn"),
                "Remove a warning from a member on the server.",
                null,
                JModularBot.instance.getJda().getRoleById(WarnPlugin.instance.getConfig().getStaffRole()),
                "unwarn <uuid> <reason>",
                true,
                false);
    }

    @Override
    public boolean onCommand(MessageReceivedEvent event, String[] args) {
        // Checking preconditions
        if(args.length < 3) {
            return false;
        }

        // 2. Grab the data in data/warns then save the UUID to the data/warns.json file
        Map<String, List<Warn>> warns = WarnPlugin.instance.getWarns();
        Object[] userAndWarn = getUserAndWarn(warns, args[1]);

        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();

        if(userAndWarn == null) {
            channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                    .setColor(Color.RED)
                    .setTitle("Unwarn Error")
                    .setDescription("There is no warn with that uuid!")
                    .build()
            ).queue();
            return true;
        }

        String userId = (String) userAndWarn[0];
        User unwarnUser = MessageUtils.extractMention(userId);
        Member unwarnMember = event.getGuild().getMember(unwarnUser);

        EmbedBuilder embed = JModularBot.getTemplate(author)
                .setColor(Color.GREEN)
                .setTitle("Unwarn Successful")
                .addField("Deleted ID", args[1], false);

        if(unwarnMember == null) {
            embed.setDescription(unwarnUser.getId() + " has been unwarned (The warn has been deleted)");
        } else {
            embed.setDescription(unwarnMember.getAsMention() + " has been unwarned (The warn has been deleted)");

        }

        warns.get(userId).remove((Warn) userAndWarn[1]);

        // 3. Send warn message
        channel.sendMessageEmbeds(embed.build()).queue();

        // 4. Send warn log message
        channel.sendMessageEmbeds(JModularBot.getTemplate(author)
                .setColor(Color.YELLOW)
                .setTitle("Log | Unwarn")
                .setDescription(MarkdownUtil.bold(unwarnUser.getId()) + " was unwarned by " + MessageUtils.boldNameAndTag(author) + " for " + getReason(args))
                .build()).queue();

        return true;
    }

    private Object[] getUserAndWarn(Map<String, List<Warn>> warns, String uuid) {
        for(String key : warns.keySet()) {
            for(Warn warn : warns.get(key)) {
                if(warn.getUuid().equals(uuid)) {
                    return new Object[]{key, warn};
                }
            }
        }

        return null;
    }

    private String getReason(String[] args) {
        StringBuilder reason = new StringBuilder(args[2]);

        for(int i = 3; i < args.length; i++) {
            reason.append(" ").append(args[i]);
        }

        return reason.toString();
    }
}
