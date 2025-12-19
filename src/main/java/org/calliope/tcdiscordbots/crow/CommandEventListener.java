package org.calliope.tcdiscordbots.crow;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.calliope.tcdiscordbots.crow.yamboard.YamboardConfig;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandEventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(CommandEventListener.class);
    private final YamboardConfig yamboardConfig;

    public CommandEventListener(YamboardConfig yamboardConfig) {
        this.yamboardConfig = yamboardConfig;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Crow Command Listener instantiated.");
    }
}
