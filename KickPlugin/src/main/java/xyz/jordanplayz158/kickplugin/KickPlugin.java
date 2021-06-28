package xyz.jordanplayz158.kickplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

public class KickPlugin extends Plugin {
    public static KickPlugin instance;

    public KickPlugin() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}