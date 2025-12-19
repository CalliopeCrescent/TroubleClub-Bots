package org.calliope.tcdiscordbots;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import org.calliope.tcdiscordbots.crow.Crow;
import org.calliope.tcdiscordbots.crow.yamboard.YamboardDB;
import org.calliope.tcdiscordbots.bot.BotJDA;
import org.calliope.tcdiscordbots.bot.BotName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    static Map<BotName, JDA> bots = new HashMap<>();

    public static void main(String[] args) {
        Map<BotName, BotJDA> botBuilders = Map.of(
                BotName.CROW, new Crow()
        );

        // Check .env & args for valid bot tokens
        for (BotName botName : BotName.values()) {
            String token = System.getenv(botName.name() + "TOKEN");
            BotJDA botBuilder = botBuilders.get(botName);
            if (token == null || botBuilder == null) {
                log.error("Bot {} could not be instantiated.", botName);
                continue;
            }

            // Instantiate bot
            try {
                bots.put(botName, botBuilder.create(token));
                log.info("Bot {} created.", botName);
            } catch (InvalidTokenException e) {
                log.error("Bot {} could not be instantiated as token was incorrect.", botName);
            }
        }

        YamboardDB.onStartup(bots.get(BotName.CROW));
    }
}