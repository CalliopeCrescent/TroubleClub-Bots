package org.calliope.tcdiscordbots.resources;

import java.util.HashMap;
import java.util.Map;

public class BotConfig {

    private static BotConfig INSTANCE;

    public static BotConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BotConfig();
        }

        return INSTANCE;
    }

    private Map<String, String> configs = new HashMap<String, String>();

    public String getConfig(String key) {
        return configs.get(key);
    }

    public void setConfig(String key, String value) {
        if (configs.containsKey(key)) {
            configs.put(key, value);
        }

        // Update config
    }
}
