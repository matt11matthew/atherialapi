package me.matthewe.api.addon;

import me.matthewe.atherial.api.addon.AtherialAddon;
import me.matthewe.atherial.api.addon.AtherialAddonDescription;

@AtherialAddonDescription(name = "Test", version = "1.0", authors = {"Matthew E"})
public class TestAddon extends AtherialAddon {

    @Override
    public void onLoad() {
        System.out.println("loaded");
    }
}
