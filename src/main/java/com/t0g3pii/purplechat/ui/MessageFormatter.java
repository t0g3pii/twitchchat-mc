package com.t0g3pii.purplechat.ui;

import com.t0g3pii.purplechat.config.PurpleChatConfig;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.Map;

public final class MessageFormatter {
    private MessageFormatter() {}

    public static Text formatText(PurpleChatConfig cfg, String channel, String user, String message, String hexColor, Map<String, String> tags) {
        // Prefix: [Twitch | channel] in Lila
        MutableText prefix = Text.literal("[")
                .styled(s -> s.withColor(TextColor.fromFormatting(Formatting.DARK_PURPLE)));
        prefix.append(Text.literal("Twitch | ")
                .styled(s -> s.withColor(TextColor.fromFormatting(Formatting.DARK_PURPLE))));
        prefix.append(Text.literal(channel)
                .styled(s -> s.withColor(TextColor.fromFormatting(Formatting.DARK_PURPLE))));
        prefix.append(Text.literal("] ")
                .styled(s -> s.withColor(TextColor.fromFormatting(Formatting.DARK_PURPLE))));

        // Username mit Twitch-Farbe (falls vorhanden)
        MutableText userText = Text.literal(user + ": ")
                .styled(s -> s.withColor(TextColor.fromFormatting(Formatting.GRAY)));
        if (cfg.useTwitchUserColor) {
            Integer rgb = parseHexColor(hexColor);
            if (rgb != null) {
                userText = Text.literal(user)
                        .styled(s -> s.withColor(TextColor.fromRgb(rgb)))
                        .append(Text.literal(": ")
                                .styled(s -> s.withColor(TextColor.fromFormatting(Formatting.GRAY))));
            }
        }

        // Nachricht in Weiß (oder Standard)
        MutableText msg = Text.literal(message)
                .styled(s -> s.withColor(TextColor.fromFormatting(Formatting.WHITE)));

        return prefix.append(userText).append(msg);
    }

    private static Integer parseHexColor(String hex) {
        if (hex == null || hex.isEmpty()) return null;
        String h = hex.startsWith("#") ? hex.substring(1) : hex;
        if (h.length() != 6) return null;
        try {
            return Integer.parseInt(h, 16);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}


