package xyz.jordanplayz158.banplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

public class BanPlugin extends Plugin {
    public static BanPlugin instance;

    public BanPlugin() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
