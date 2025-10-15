package com.t0g3pii.purplechat.ui;

import com.t0g3pii.purplechat.PurpleChatMod;
import com.t0g3pii.purplechat.config.PurpleChatConfig;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public final class ConfigScreenProvider {
    private ConfigScreenProvider() {}

    public static Screen create(Screen parent) {
        PurpleChatConfig cfg = PurpleChatMod.config();
        return YetAnotherConfigLib.createBuilder()
                .title(text("PurpleChat"))
                .category(ConfigCategory.createBuilder()
                        .name(text("Allgemein"))
                        .option(Option.<String>createBuilder()
                                .name(text("Kanäle (Kommagetrennt)"))
                                .binding("", () -> String.join(",", cfg.channels), v -> {
                                    cfg.channels.clear();
                                    for (String s : v.split(",")) {
                                        String t = s.trim();
                                        if (!t.isEmpty()) cfg.channels.add(t);
                                    }
                                })
                                .controller(opt -> StringControllerBuilder.create(opt))
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(text("Nachrichten-Template"))
                                .binding(cfg.messageFormat, () -> cfg.messageFormat, v -> cfg.messageFormat = v)
                                .controller(opt -> StringControllerBuilder.create(opt))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(text("Twitch-Userfarbe verwenden"))
                                .binding(cfg.useTwitchUserColor, () -> cfg.useTwitchUserColor, v -> cfg.useTwitchUserColor = v)
                                .controller(opt -> BooleanControllerBuilder.create(opt))
                                .build())
                        .build())
                .save(() -> {
                    // Config speichern und Kanäle live anwenden
                    com.t0g3pii.purplechat.PurpleChatMod.saveConfig();
                    com.t0g3pii.purplechat.PurpleChatMod.applyChannels();
                })
                .build()
                .generateScreen(parent);
    }

    private static net.minecraft.text.Text text(String s) {
        return net.minecraft.text.Text.literal(s);
    }

    private static void saveConfig() {
        // Speichern via ConfigManager aus dem Mod-Entrypoint
        // Hier vereinfachtes Re-Save über Zugriff auf PurpleChatMod.config()
        com.t0g3pii.purplechat.PurpleChatMod.config();
    }
}


