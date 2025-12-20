package org.calliope.tcdiscordbots.crow.yamboard;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEmojiEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.calliope.tcdiscordbots.crow.invokes.slash.SetDetectChannel;
import org.calliope.tcdiscordbots.crow.invokes.slash.SetPostChannel;
import org.calliope.tcdiscordbots.resources.commands.slash.SlashManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YamboardEventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(YamboardEventListener.class);
    private final YamboardConfig yamboardConfig = new YamboardConfig("yamboard");
    private TextChannel detectChannel, postChannel;
    private Emoji yamEmoji;

    public YamboardEventListener(SlashManager slashManager) {
        slashManager.addSlash(new SetDetectChannel(this, yamboardConfig));
        slashManager.addSlash(new SetPostChannel(this, yamboardConfig));
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Crow Reaction Listener instantiated.");

        detectChannel = event.getJDA().getTextChannelById(yamboardConfig.getDetectChannelId());
        postChannel = event.getJDA().getTextChannelById(yamboardConfig.getPostChannelId());
        yamEmoji = Emoji.fromFormatted(yamboardConfig.getReactionId());
    }

    @Override
    public void onMessageReactionRemoveEmoji(@NotNull MessageReactionRemoveEmojiEvent event) {
        if (detectChannel != event.getChannel()) return;
        if (!yamEmoji.equals(event.getEmoji())) return;

        String yamId = YamboardDB.isMessageInHistory(event.getMessageId());
        if (yamId.isEmpty()) return;

        postChannel.deleteMessageById(yamId).queue();
        YamboardDB.deleteYamHistoryByMessage(event.getMessageId());
    }

    @Override
    public void onMessageReactionRemoveAll(@NotNull MessageReactionRemoveAllEvent event) {
        if (detectChannel != event.getChannel()) return;

        String yamId = YamboardDB.isMessageInHistory(event.getMessageId());
        if (yamId.isEmpty()) return;

        postChannel.deleteMessageById(yamId).queue();
        YamboardDB.deleteYamHistoryByMessage(event.getMessageId());
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if (event.getUser() == null) return;
        if (event.getUser().isBot()) return;
        if (detectChannel != event.getChannel()) return;
        if (!yamEmoji.equals(event.getEmoji())) return;

        event.retrieveMessage().queue((message) -> {
            int reactionIndex = message.getReactions().indexOf(event.getReaction());
            if (reactionIndex != -1) return;

            String yamId = YamboardDB.isMessageInHistory(event.getMessageId());
            if (yamId.isEmpty()) return;

            postChannel.deleteMessageById(yamId).queue();
            YamboardDB.deleteYamHistoryByMessage(event.getMessageId());
        });
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (event.getUser() == null) return;
        if (event.getUser().isBot()) return;
        if (detectChannel != event.getChannel()) return;
        if (!yamEmoji.equals(event.getEmoji())) return;

        event.retrieveMessage().queue((message) -> {
            int reactionIndex = message.getReactions().indexOf(event.getReaction());
            if (reactionIndex == -1) return;
            if (message.getReactions().get(reactionIndex).getCount() != 1) return;

            assert event.getMember() != null;
            postChannel.sendMessageEmbeds(Yamboard.createEmbeds(message, event.getMember())).queue((yamMessage) -> {
                YamboardDB.postYamHistory(message.getId(), yamMessage.getId());
            });
        });
    }

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        if (detectChannel != event.getChannel()) return;

        String yamId = YamboardDB.isMessageInHistory(event.getMessageId());
        if (yamId.isEmpty()) return;

        postChannel.deleteMessageById(yamId).queue();
        YamboardDB.deleteYamHistoryByMessage(event.getMessageId());
    }

    public void updateDetectChannel(TextChannel channel) {
        detectChannel = channel;
    }

    public void updatePostChannel(TextChannel channel) {
        postChannel = channel;
    }
}
