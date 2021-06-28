package xyz.jordanplayz158.warnplugin;

import lombok.Getter;
import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

import java.io.File;

public class WarnPlugin extends Plugin {
    public static WarnPlugin instance;
    @Getter
    private final File warnsFile = new File(getDataFolder(), "warns.json");

    public WarnPlugin() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
