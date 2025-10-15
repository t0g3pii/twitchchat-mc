package com.t0g3pii.purplechat.integrations;

import net.fabricmc.loader.api.FabricLoader;

import java.util.Optional;

public final class ModMenuIntegration {
    private ModMenuIntegration() {}

    public static boolean isAvailable() {
        return FabricLoader.getInstance().isModLoaded("modmenu");
    }

    public static Optional<Runnable> getConfigOpener() {
        if (!isAvailable()) return Optional.empty();
        // YACL Screen folgt später; hier nur Platzhalter
        return Optional.of(() -> {
            // no-op for now
        });
    }
}


