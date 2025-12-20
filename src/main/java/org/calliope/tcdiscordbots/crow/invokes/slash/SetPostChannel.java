package org.calliope.tcdiscordbots.crow.invokes.slash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import org.calliope.tcdiscordbots.crow.yamboard.YamboardConfig;
import org.calliope.tcdiscordbots.crow.yamboard.YamboardEventListener;
import org.calliope.tcdiscordbots.resources.RandomColor;
import org.calliope.tcdiscordbots.resources.commands.slash.Slash;

import java.util.concurrent.TimeUnit;

public class SetPostChannel implements Slash {

    private final YamboardEventListener listener;
    private final YamboardConfig config;

    public SetPostChannel(YamboardEventListener listener, YamboardConfig config) {
        this.listener = listener;
        this.config = config;
    }

    @Override
    public void invoke(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getChannelType() != ChannelType.TEXT) return;

        TextChannel channel = event.getChannel().asTextChannel();
        listener.updatePostChannel(channel);
        boolean setConfig = config.setPostChannelId(channel.getId());

        Member selfMember = channel.getGuild().getSelfMember();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(selfMember.getNickname(), null, selfMember.getEffectiveAvatarUrl());
        builder.setColor(RandomColor.getRandomColor());
        builder.setTitle("Update post channel");

        if (setConfig) {
            builder.setDescription("✅ Post channel has been updated to the current channel: " + channel.getJumpUrl());
        } else {
            builder.setDescription("❌ Post channel could not be updated to the current channel.");
        }

        event.replyEmbeds(builder.build()).queue(interactionHook -> {
            interactionHook.deleteOriginal().queueAfter(5, TimeUnit.SECONDS);
        });
    }

    @Override
    public String name() {
        return "post-channel";
    }

    @Override
    public String description() {
        return "Sets the channel id to post yams.";
    }

    @Override
    public DefaultMemberPermissions defaultPermission() {
        return DefaultMemberPermissions.DISABLED;
    }
}