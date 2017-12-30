package me.matthewe.atherial.api;

import me.matthewe.atherial.api.addon.AtherialAddonManager;

import java.io.File;
import java.net.URL;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialApi {
    private static AtherialApi instance;
    private boolean isSpigotPlugin;

    public AtherialApi() {
        instance = this;
    }

    public static AtherialApi getInstance() {
        if (instance == null) {
            instance = new AtherialApi();
        }
        return instance;
    }

    public void loadAddons(File file, ClassLoader classLoader) {
        AtherialAddonManager.getManager().loadAddons(file, classLoader);
    }

    public void setSpigotPlugin(boolean isSpigotPlugin) {
        this.isSpigotPlugin = isSpigotPlugin;
    }

    public boolean isSpigotPlugin() {
        return isSpigotPlugin;
    }
}
