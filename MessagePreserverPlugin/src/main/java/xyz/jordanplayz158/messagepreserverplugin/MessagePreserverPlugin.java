package xyz.jordanplayz158.messagepreserverplugin;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import xyz.jordanplayz158.jmodularbot.managers.EventManager;
import xyz.jordanplayz158.jmodularbot.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;

public class MessagePreserverPlugin extends JavaPlugin {
    public static MessagePreserverPlugin instance;
    public static File usersToPreserve;
    public static List<Long> userIds;

    @Override
    public void onEnable() {
        instance = this;

        getDataFolder().mkdir();
        usersToPreserve = new File(getDataFolder(), "users.json");

        try {
            loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventManager.addEvents(this, new ReadyListener());
    }

    public static void loadUsers() throws IOException {
        if(usersToPreserve.createNewFile()) {
            FileWriter fileWriter = new FileWriter(usersToPreserve);
            fileWriter.write("[]");
            fileWriter.close();
        }

        Reader reader = Files.newBufferedReader(usersToPreserve.toPath());
        userIds = new Gson().fromJson(reader, new TypeToken<List<Long>>() {}.getType());

        reader.close();
    }

    public static void saveUsers() throws IOException {
        Writer writer = new FileWriter(usersToPreserve);

        // convert users list to JSON file
        new Gson().toJson(userIds, writer);

        // close writer
        writer.close();
    }
}
