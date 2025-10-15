package com.t0g3pii.purplechat.config;

import java.util.ArrayList;
import java.util.List;

public final class PurpleChatConfig {
    public List<String> channels = new ArrayList<>();
    public String messageFormat = "§5[Twitch | {channel}] §7{user}: §f{message}";
    public boolean useTwitchUserColor = true;
    public String logLevel = "INFO"; // OFF, ERROR, WARN, INFO, DEBUG, TRACE

    public static PurpleChatConfig createDefault() {
        PurpleChatConfig cfg = new PurpleChatConfig();
        // Example channel list empty by default
        return cfg;
    }
}


