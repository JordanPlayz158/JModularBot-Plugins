package xyz.jordanplayz158.unwarnplugin;

import lombok.Getter;
import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

import java.io.File;

public class UnwarnPlugin extends Plugin {
    public static UnwarnPlugin instance;
    @Getter
    private final File warnsFile = new File(getDataFolder(), "warns.json");

    public UnwarnPlugin() {
        instance = this;
        EventManager.addEvents(this, new ReadyListener());
    }
}
