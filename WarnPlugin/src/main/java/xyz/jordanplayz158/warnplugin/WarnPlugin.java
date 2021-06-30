package xyz.jordanplayz158.warnplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.Setter;
import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
public class WarnPlugin extends Plugin {
    public static WarnPlugin instance;
    private File warnsFile;
    private Config config;
    @Setter
    public Map<String, List<Warn>> warns = new HashMap<>();
    private SaveWarns saveWarns;


    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdir();
        final File configFile = new File(getDataFolder(), "config.yml");
        warnsFile = new File(getDataFolder(), "warns.json");

        try {
            if (!configFile.exists()) {
                InputStream is = Objects.requireNonNull(WarnPlugin.class.getResourceAsStream("/" + configFile.getName()));

                Files.copy(is, configFile.toPath());
            }

            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            config = objectMapper.readValue(configFile, Config.class);

            warnsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveWarns = new SaveWarns(config.getAutosave());

        EventManager.addEvents(this, new ReadyListener());
    }

    @Override
    public void onDisable() {
        saveWarns.run();
        saveWarns.cancel();
    }
}
