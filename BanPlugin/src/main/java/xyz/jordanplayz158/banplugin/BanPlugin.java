package xyz.jordanplayz158.banplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.java.JavaPlugin;

public class BanPlugin extends JavaPlugin {
    public static BanPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
