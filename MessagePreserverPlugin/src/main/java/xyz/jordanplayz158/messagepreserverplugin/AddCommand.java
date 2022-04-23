package xyz.jordanplayz158.messagepreserverplugin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import xyz.jordanplayz158.jmodularbot.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class AddCommand extends Command {
    public AddCommand() {
        super("add",
                null,
                "Add a user to preserve messages for.",
                Permission.MESSAGE_MANAGE,
                null,
                "add <userId/mention>",
                false,
                false);
    }

    @Override
    public boolean onCommand(MessageReceivedEvent event, String[] args) {
        if(args.length < 2) {
            return false;
        }

        Guild guild = event.getGuild();
        TextChannel channel = event.getTextChannel();
        String userString = args[1];
        Member member;

        if(userString.startsWith("<@")) {
            try {
                long userId = Long.parseLong(userString.substring(2, userString.length() - 1));
                member = guild.getMemberById(userId);
            } catch (NumberFormatException e) {
                member = null;
            }
        } else {
            member = guild.getMemberById(userString);
        }

        if(member == null) {
            channel.sendMessage("Invalid User ID/Mention provided!").queue();
            return true;
        }

        MessagePreserverPlugin.userIds.add(member.getIdLong());
        try {
            MessagePreserverPlugin.saveUsers();
            MessagePreserverPlugin.loadUsers();

            channel.sendMessage("User added successfully.").queue();
        } catch (IOException e) {
            channel.sendMessage("Unknown error occured while adding user to file.").queue();
        }

        return true;
    }
}
