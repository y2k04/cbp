package com.y2k4.cbp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class CBP implements ModInitializer {
    public static boolean mountKeyPressed = false;
    private static KeyBinding mountKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.cbp.ridechestboat",
            InputUtil.Type.KEYSYM,
            InputUtil.GLFW_KEY_LEFT_ALT,
            "category.cbp.keybinds"
    ));

    @Override
    public void onInitialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> mountKeyPressed = mountKey.isPressed());
    }
}
