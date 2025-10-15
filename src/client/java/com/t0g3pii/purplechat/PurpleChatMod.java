package com.t0g3pii.purplechat;

import com.t0g3pii.purplechat.config.ConfigManager;
import com.t0g3pii.purplechat.config.PurpleChatConfig;
import com.t0g3pii.purplechat.twitch.TwitchService;
import com.t0g3pii.purplechat.client.KeyBindings;
import com.t0g3pii.purplechat.ui.ConfigScreenProvider;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PurpleChatMod implements ClientModInitializer {
    public static final String MOD_ID = "purplechat";
    private static final Logger LOGGER = LoggerFactory.getLogger("PurpleChat");

    private static final ConfigManager CONFIG_MANAGER = new ConfigManager();
    private static final TwitchService TWITCH = new TwitchService();

    public static PurpleChatConfig config() {
        return CONFIG_MANAGER.get();
    }

    @Override
    public void onInitializeClient() {
        CONFIG_MANAGER.loadOrCreate();
        LOGGER.info("PurpleChat geladen (Client). Channels: {}", config().channels.size());
        TWITCH.start(config());
        KeyBindings.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyBindings.openConfig.wasPressed()) {
                if (client != null) client.setScreen(ConfigScreenProvider.create(client.currentScreen));
            }
        });
    }

    public static void applyChannels() {
        TWITCH.updateChannels(config());
    }

    public static void saveConfig() {
        CONFIG_MANAGER.save();
    }
}


