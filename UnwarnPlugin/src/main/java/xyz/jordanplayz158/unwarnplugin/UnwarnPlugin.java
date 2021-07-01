package xyz.jordanplayz158.unwarnplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.java.JavaPlugin;

public class UnwarnPlugin extends JavaPlugin {
    public static UnwarnPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
