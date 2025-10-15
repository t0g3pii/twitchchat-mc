package com.t0g3pii.purplechat.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE_NAME = "purplechat.json";

    private PurpleChatConfig current = PurpleChatConfig.createDefault();

    public PurpleChatConfig get() {
        return current;
    }

    public void loadOrCreate() {
        Path cfgDir = FabricLoader.getInstance().getConfigDir();
        Path cfgFile = cfgDir.resolve(CONFIG_FILE_NAME);
        if (Files.exists(cfgFile)) {
            try (Reader r = Files.newBufferedReader(cfgFile)) {
                PurpleChatConfig loaded = GSON.fromJson(r, PurpleChatConfig.class);
                if (loaded != null) {
                    current = loaded;
                }
            } catch (IOException e) {
                // keep defaults
            }
        } else {
            save();
        }
    }

    public void save() {
        Path cfgDir = FabricLoader.getInstance().getConfigDir();
        Path cfgFile = cfgDir.resolve(CONFIG_FILE_NAME);
        try {
            Files.createDirectories(cfgDir);
            try (Writer w = Files.newBufferedWriter(cfgFile)) {
                GSON.toJson(current, w);
            }
        } catch (IOException e) {
            // ignore
        }
    }
}


