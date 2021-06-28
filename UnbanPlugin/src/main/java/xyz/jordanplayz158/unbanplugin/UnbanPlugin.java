package xyz.jordanplayz158.unbanplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

public class UnbanPlugin extends Plugin {
    public static UnbanPlugin instance;

    public UnbanPlugin() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
