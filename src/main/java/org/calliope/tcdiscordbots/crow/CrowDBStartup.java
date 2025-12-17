package org.calliope.tcdiscordbots.crow;

import net.dv8tion.jda.api.JDA;
import org.calliope.tcdiscordbots.resources.sqlite.SQLiteDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CrowDBStartup {

    private static final Logger log = LoggerFactory.getLogger(CrowDBStartup.class);

    public static void onStartup(JDA jda) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                Statement statement = connection.createStatement();
                ) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS yamboard(" +
                            "key TEXT PRIMARY KEY," +
                            "value TEXT NOT NULL" +
                            ")"
            );

            statement.execute(
                    "INSERT OR IGNORE INTO yamboard(key, value) " +
                            "VALUES " +
                            "('guild_id', 0)," +
                            "('detect_channel_id', 0)," +
                            "('post_channel_id', 0)"
            );

            statement.executeBatch();
        } catch (SQLException e) {
            log.error("An error occurred while trying to connect to database.", e);
        }
    }
}
