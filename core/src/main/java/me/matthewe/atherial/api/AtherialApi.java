package me.matthewe.atherial.api;

//import me.matthewe.atherial.api.addon.AtherialAddonManager;

import me.matthewe.atherial.api.addon.AtherialAddon;
import me.matthewe.atherial.api.addon.AtherialAddonManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public void loadAddons(File file) {
        AtherialAddonManager.getManager().loadAddons(file);
    }

    public boolean isSpigotPlugin() {
        return isSpigotPlugin;
    }

    public void setSpigotPlugin(boolean isSpigotPlugin) {
        this.isSpigotPlugin = isSpigotPlugin;
    }

    public List<AtherialAddon> getAddons() {
        return new ArrayList<>(AtherialAddonManager.getManager().getAddonMap().values());
    }
}
