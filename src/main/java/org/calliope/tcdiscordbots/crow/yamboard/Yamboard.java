package org.calliope.tcdiscordbots.crow.yamboard;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.calliope.tcdiscordbots.resources.RandomColor;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Yamboard {

    protected static List<MessageEmbed> createEmbeds(Message message, Member yammer) {
        List<EmbedBuilder> builders = new ArrayList<>();

        String yammerName = yammer.getNickname();
        if (yammerName == null) yammerName = yammer.getEffectiveName();

        String messageName = message.getMember().getNickname();
        if (messageName == null) messageName = yammer.getEffectiveName();

        EmbedBuilder textBuilder = new EmbedBuilder();
        textBuilder.setColor(RandomColor.getRandomColor());
        textBuilder.setAuthor(messageName, null, message.getMember().getEffectiveAvatarUrl());
        textBuilder.setTitle("Yammed by " + yammerName, message.getJumpUrl());
        textBuilder.setDescription(message.getContentRaw());
        textBuilder.setFooter(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a", Locale.US)));
        builders.add(textBuilder);

        int attachments = 0;
        for (Message.Attachment attach : message.getAttachments()) {
            if (!attach.isImage() && !attach.isVideo()) continue;

            if (attachments == 0) {
                textBuilder.setImage(attach.getUrl());
                attachments = 1;
                continue;
            }

            EmbedBuilder attachBuilder = new EmbedBuilder();
            attachBuilder.setUrl(message.getJumpUrl());
            attachBuilder.setImage(attach.getUrl());
            builders.add(attachBuilder);
            attachments++;

            if (attachments == 4) {
                break;
            }
        }

        List<MessageEmbed> embeds = new ArrayList<>();
        for (EmbedBuilder completeBuilder : builders) {
            embeds.add(completeBuilder.build());
        }

        return embeds;
    }
}
