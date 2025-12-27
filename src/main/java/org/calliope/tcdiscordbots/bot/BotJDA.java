package org.calliope.tcdiscordbots.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.List;

public interface BotJDA {

    default JDA create(String token) {
        return JDABuilder
                .createDefault(token)
                .enableIntents(intents())
                .setMemberCachePolicy(cachePolicy())
                .addEventListeners(listeners().toArray())
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .build();
    }

    List<GatewayIntent> intents();

    MemberCachePolicy cachePolicy();

    List<ListenerAdapter> listeners();
}
