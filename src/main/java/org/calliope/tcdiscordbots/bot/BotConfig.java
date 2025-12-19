package org.calliope.tcdiscordbots.bot;

import org.calliope.tcdiscordbots.resources.SQLiteDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BotConfig {

    private static final Logger log = LoggerFactory.getLogger(BotConfig.class);
    private Map<String, String> configs = new HashMap<String, String>();

    public BotConfig(String tableConfig) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " +  tableConfig
                )) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    configs.put(resultSet.getString("key"), resultSet.getString("value"));
                }
            }
        } catch (SQLException e) {
            log.error("An error occurred while trying to retrieve configs for {}", tableConfig, e);
        }
    }

    protected String getConfig(String key) {
        return configs.get(key);
    }

    protected boolean setConfig(String key, String value) {
        if (!configs.containsKey(key)) {
            return false;
        }

        configs.put(key, value);
        return true;
    }
}
