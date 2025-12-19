package org.calliope.tcdiscordbots.crow;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.calliope.tcdiscordbots.crow.yamboard.YamboardConfig;
import org.calliope.tcdiscordbots.crow.yamboard.YamboardEventListener;
import org.calliope.tcdiscordbots.bot.BotJDA;

import java.util.List;

public class Crow implements BotJDA {

    @Override
    public List<GatewayIntent> intents() {
        return List.of(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MESSAGE_REACTIONS
        );
    }

    @Override
    public MemberCachePolicy cachePolicy() {
        return MemberCachePolicy.ALL;
    }

    @Override
    public List<ListenerAdapter> listeners() {
        YamboardConfig yamboardConfig = new YamboardConfig("yamboard");
        return List.of(
                new YamboardEventListener(yamboardConfig),
                new CommandEventListener(yamboardConfig)
        );
    }
}
