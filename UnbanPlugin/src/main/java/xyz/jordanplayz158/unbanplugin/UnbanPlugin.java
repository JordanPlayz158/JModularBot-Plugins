package xyz.jordanplayz158.unbanplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.java.JavaPlugin;

public class UnbanPlugin extends JavaPlugin {
    public static UnbanPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
