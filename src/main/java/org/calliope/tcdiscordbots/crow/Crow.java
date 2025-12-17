package org.calliope.tcdiscordbots.crow;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.calliope.tcdiscordbots.crow.eventlisteners.ReactionEventListener;
import org.calliope.tcdiscordbots.resources.BotJDA;

import java.util.List;

public class Crow implements BotJDA {

    @Override
    public List<GatewayIntent> intents() {
        return List.of(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS
        );
    }

    @Override
    public MemberCachePolicy cachePolicy() {
        return MemberCachePolicy.ALL;
    }

    @Override
    public List<ListenerAdapter> listeners() {
        return List.of(
                new ReactionEventListener()
        );
    }
}
