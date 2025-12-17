package org.calliope.tcdiscordbots.crow.yamboard;

import org.calliope.tcdiscordbots.resources.sqlite.SQLiteDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class YamboardDB {

    private static final Logger log = LoggerFactory.getLogger(YamboardDB.class);

    public static boolean setGuildId(String guildId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                    "UPDATE yamboard SET value = ? WHERE key = 'guild_id'"
                )) {
            statement.setString(1, guildId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            log.error("An error occurred while trying to update guild_id.", e);
            return false;
        }
    }

    public static boolean setDetectChannelId(String detectChannelId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE yamboard SET value = ? WHERE key = 'detect_channel_id'"
                )) {

        } catch (SQLException e) {
            log.error("An error occurred while trying to update detect_channel_id.", e);
            return false;
        }

        return false;
    }

    public static boolean setPostChannelId(String postChannelId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE yamboard SET value = ? WHERE key = 'post_channel_id'"
                )) {

        } catch (SQLException e) {
            log.error("An error occurred while trying to update post_channel_id.", e);
            return false;
        }

        return false;
    }
}
