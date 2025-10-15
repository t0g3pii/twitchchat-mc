package com.t0g3pii.purplechat.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class KeyBindings {
    private KeyBindings() {}

    public static KeyBinding openConfig;

    public static void register() {
        openConfig = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.purplechat.open_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.purplechat"
        ));
    }
}


