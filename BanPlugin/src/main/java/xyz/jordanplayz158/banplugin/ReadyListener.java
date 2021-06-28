package xyz.jordanplayz158.banplugin;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jordanplayz158.jmodularbot.managers.CommandManager;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        CommandManager.addCommands(BanPlugin.instance, new BanCommand());
    }
}
