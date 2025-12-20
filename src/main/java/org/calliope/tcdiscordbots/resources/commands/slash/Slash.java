package org.calliope.tcdiscordbots.resources.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

public interface Slash {
    void invoke(SlashCommandInteractionEvent event);
    String name();
    String description();
    DefaultMemberPermissions defaultPermission();
}
