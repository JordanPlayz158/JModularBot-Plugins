package xyz.jordanplayz158.muteplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

public class MutePlugin extends Plugin {
    public static MutePlugin instance;

    public MutePlugin() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
