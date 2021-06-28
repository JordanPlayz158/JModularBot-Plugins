package xyz.jordanplayz158.roleonmemberjoinplugin;

import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

import java.io.File;

public class RoleOnMemberJoinPlugin extends Plugin {
    public static RoleOnMemberJoinPlugin instance;
    private final File memberJoin = new File(getDataFolder(), "memberJoin.json");

    public RoleOnMemberJoinPlugin() {
        instance = this;
        EventManager.addEvents(this, new MemberJoinListener());
    }
}
