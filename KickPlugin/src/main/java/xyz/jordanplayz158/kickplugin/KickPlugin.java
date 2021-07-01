package xyz.jordanplayz158.kickplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.java.JavaPlugin;

public class KickPlugin extends JavaPlugin {
    public static KickPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}