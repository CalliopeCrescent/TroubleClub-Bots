package org.calliope.tcdiscordbots.crow.yamboard;

import org.calliope.tcdiscordbots.bot.BotConfig;

public class YamboardConfig extends BotConfig {

    public YamboardConfig(String tableConfig) {
        super(tableConfig);
    }

    public String getGuildId() {
        return getConfig("guild_id");
    }

    public boolean setGuildId(String guildId) {
        boolean configFound = setConfig("guild_id", guildId);
        if (configFound) {
            return YamboardDB.setGuildId(guildId);
        }

        return false;
    }

    public String getDetectChannelId() {
        return getConfig("detect_channel_id");
    }

    public boolean setDetectChannelId(String detectChannelId) {
        boolean configFound = setConfig("detect_channel_id", detectChannelId);
        if (configFound) {
            return YamboardDB.setDetectChannelId(detectChannelId);
        }

        return false;
    }

    public String getPostChannelId() {
        return getConfig("post_channel_id");
    }

    public boolean setPostChannelId(String postChannelId) {
        boolean  configFound = setConfig("post_channel_id", postChannelId);
        if (configFound) {
            return YamboardDB.setPostChannelId(postChannelId);
        }

        return false;
    }

    public String getReactionId() {
        return getConfig("reaction_id");
    }

    public boolean setReactionId(String reactionId) {
        boolean configFound = setConfig("reaction_id", reactionId);
        if (configFound) {
            return YamboardDB.setReactionId(reactionId);
        }

        return false;
    }
}
