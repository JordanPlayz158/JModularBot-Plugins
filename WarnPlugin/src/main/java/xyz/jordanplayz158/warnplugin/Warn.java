package xyz.jordanplayz158.warnplugin;

import lombok.Getter;

@Getter
public class Warn {
    private final String uuid;
    private final String reason;

    public Warn(String uuid, String reason) {
        this.uuid = uuid;
        this.reason = reason;
    }
}
