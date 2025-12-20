package org.calliope.tcdiscordbots.resources.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlashManager {

    private static final Logger log = LoggerFactory.getLogger(SlashManager.class);
    private final HashMap<String, Slash> slashes = new HashMap<>();

    public SlashManager() {}

    public SlashManager(List<Slash> slashes) {
        for (Slash slash : slashes) {
            addSlash(slash);
        }
    }

    public void addSlash(Slash slash) {
        if (slashes.containsKey(slash.name())) {
            log.warn("Slash command {} already exists in SlashManager.", slash.name());
            return;
        }

        slashes.put(slash.name(), slash);
    }

    public List<CommandData> getSlashData() {
        List<CommandData> commands = new ArrayList<>();
        for (Slash slash : slashes.values()) {
            commands.add(
                    Commands.slash(slash.name(), slash.description()).setDefaultPermissions(slash.defaultPermission())
            );
        }

        return commands;
    }

    public void handleEvent(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;

        Slash slash = slashes.get(event.getName());
        if (slash == null) {
            log.error("Slash command {} does not exist in SlashManager.", event.getName());
            return;
        }

        slash.invoke(event);
    }
}
