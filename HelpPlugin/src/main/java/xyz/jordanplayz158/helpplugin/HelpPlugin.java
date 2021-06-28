package xyz.jordanplayz158.helpplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

public class HelpPlugin extends Plugin {
    public static HelpPlugin instance;

    public HelpPlugin() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
