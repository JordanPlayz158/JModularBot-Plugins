package xyz.jordanplayz158.muteplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

public class MutePlugin extends JavaPlugin {
    public static MutePlugin instance;
    @Getter
    private Config config;

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdir();
        final File configFile = new File(getDataFolder(), "config.yml");

        try {
            if (!configFile.exists()) {
                InputStream is = Objects.requireNonNull(MutePlugin.class.getResourceAsStream("/" + configFile.getName()));
                Files.copy(is, configFile.toPath());
            }

            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            config = objectMapper.readValue(configFile, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventManager.addEvents(this, new ReadyListener());
    }
}
