package org.calliope.tcdiscordbots.crow.yamboard;

import net.dv8tion.jda.api.JDA;
import org.calliope.tcdiscordbots.resources.SQLiteDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class YamboardDB {

    private static final Logger log = LoggerFactory.getLogger(YamboardDB.class);

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
                            "('post_channel_id', 0)," +
                            "('reaction_id', 'U+1f360')"
            );

            statement.execute(
                    "CREATE TABLE IF NOT EXISTS yamboard_history(" +
                            "history_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "message_id TEXT NOT NULL," +
                            "yam_id TEXT NOT NULL" +
                            ")"
            );

            statement.executeBatch();
        } catch (SQLException e) {
            log.error("An error occurred while trying to connect to database.", e);
        }
    }

    protected static boolean setGuildId(String guildId) {
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

    protected static boolean setDetectChannelId(String detectChannelId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE yamboard SET value = ? WHERE key = 'detect_channel_id'"
                )) {
            statement.setString(1, detectChannelId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            log.error("An error occurred while trying to update detect_channel_id.", e);
            return false;
        }
    }

    protected static boolean setPostChannelId(String postChannelId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE yamboard SET value = ? WHERE key = 'post_channel_id'"
                )) {
            statement.setString(1, postChannelId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            log.error("An error occurred while trying to update post_channel_id.", e);
            return false;
        }
    }

    protected static boolean setReactionId(String reactionId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE yamboard SET value = ? WHERE key = 'reaction_id'"
                )) {
            statement.setString(1, reactionId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            log.error("An error occurred while trying to update reaction_id.", e);
            return false;
        }
    }

    protected static boolean postYamHistory(String messageId, String yamId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO yamboard_history(message_id, yam_id) VALUES (?, ?)"
                )) {
            statement.setString(1, messageId);
            statement.setString(2, yamId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            log.error("An error occurred while trying to post yam history.", e);
            return false;
        }
    }

    protected static boolean deleteYamHistoryByMessage(String messageId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM yamboard_history WHERE message_id = ?"
                )) {
            statement.setString(1, messageId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            log.error("An  error occurred while trying to delete yam history.", e);
            return false;
        }
    }

    protected static String isMessageInHistory(String messageId) {
        try (
                Connection connection = SQLiteDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                    "SELECT yam_id FROM yamboard_history WHERE message_id = ?"
                )) {
            statement.setString(1, messageId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("yam_id");
                }
            }
        } catch (SQLException e) {
            log.error("An error occurred while trying to check if message is in history.", e);
        }

        return "";
    }
}
