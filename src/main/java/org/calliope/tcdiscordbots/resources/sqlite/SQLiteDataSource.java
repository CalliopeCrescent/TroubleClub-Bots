package org.calliope.tcdiscordbots.resources.sqlite;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDataSource {

    private static final Logger log = LoggerFactory.getLogger(SQLiteDataSource.class);
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;


    private SQLiteDataSource() {}

    static {
        String databaseName = "database";

        try {
            final File databaseFile = new File(databaseName + ".db");

            if (!databaseFile.exists()) {
                if (databaseFile.createNewFile())
                    log.info("Created database file: {}.db", databaseName);
                else
                    log.error("Failed to create database file: {}.db", databaseName);
            }
        } catch (IOException e) {
            log.error("Error reading database file");
        }

        config.setJdbcUrl("jdbc:sqlite:" + databaseName + ".db");
        config.setConnectionTestQuery("SELECT 1");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setLeakDetectionThreshold(60000);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();

        // Enable foreign keys on connection
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "PRAGMA foreign_keys = ON"
            );
        } catch (SQLException e) {
            log.error("Could not execute foreign key constraint.");
        }

        return connection;
    }
}
