package xyz.jordanplayz158.messagepreserverplugin;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jordanplayz158.jmodularbot.managers.CommandManager;
import xyz.jordanplayz158.jmodularbot.managers.EventManager;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        CommandManager.addCommands(MessagePreserverPlugin.instance, new AddCommand());
        EventManager.addEvents(MessagePreserverPlugin.instance, new MessageListener());
    }
}
