package org.calliope.tcdiscordbots.crow;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.calliope.tcdiscordbots.resources.commands.slash.SlashManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvokeEventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(InvokeEventListener.class);
    private SlashManager slashManager;

    public InvokeEventListener(SlashManager slashManager) {
        this.slashManager = slashManager;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Crow Command Listener instantiated.");

        for (Guild guild : event.getJDA().getGuilds()) {
            guild.updateCommands().addCommands(slashManager.getSlashData()).queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        slashManager.handleEvent(event);
    }
}
