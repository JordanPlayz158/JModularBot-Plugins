package xyz.jordanplayz158.helpplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.java.JavaPlugin;

public class HelpPlugin extends JavaPlugin {
    public static HelpPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
