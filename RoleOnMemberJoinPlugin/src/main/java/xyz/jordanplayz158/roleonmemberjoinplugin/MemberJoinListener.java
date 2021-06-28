package xyz.jordanplayz158.roleonmemberjoinplugin;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MemberJoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        event.getGuild().addRoleToMember(
                event.getMember(),
                Objects.requireNonNull(event.getGuild().getRoleById(780073237743665213L))
        ).queue();
    }
}
