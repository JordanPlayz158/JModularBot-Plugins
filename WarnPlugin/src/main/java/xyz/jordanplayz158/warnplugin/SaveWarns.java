package xyz.jordanplayz158.warnplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SaveWarns extends TimerTask {
    private final Timer timer = new Timer();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private int map = -1;

    public SaveWarns(int minutes) {
        if(minutes <= 0) {
            minutes = 5;
        }

        timer.scheduleAtFixedRate(this, 0, minutes * 60 * 1000L);
    }

    @Override
    public void run() {
        Map<String, List<Warn>> warns = WarnPlugin.instance.getWarns();

        if(warns.size() == map) {
            return;
        }

        try {
            Writer writer = Files.newBufferedWriter(WarnPlugin.instance.getWarnsFile().toPath());

            gson.toJson(warns, writer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map = warns.size();
    }
}
