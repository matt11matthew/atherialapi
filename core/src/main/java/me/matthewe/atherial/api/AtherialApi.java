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

    /**
     * Constructs a new AtherialApi.
     */
    public AtherialApi() {
        instance = this;
    }

    /**
     * Getter for property 'instance'.
     *
     * @return Value for property 'instance'.
     */
    public static AtherialApi getInstance() {
        if (instance == null) {
            instance = new AtherialApi();
        }
        return instance;
    }

    public void loadAddons(File file) {
        AtherialAddonManager.getManager().loadAddons(file);
    }

    /**
     * Getter for property 'spigotPlugin'.
     *
     * @return Value for property 'spigotPlugin'.
     */
    public boolean isSpigotPlugin() {
        return isSpigotPlugin;
    }

    /**
     * Setter for property 'spigotPlugin'.
     *
     * @param isSpigotPlugin Value to set for property 'spigotPlugin'.
     */
    public void setSpigotPlugin(boolean isSpigotPlugin) {
        this.isSpigotPlugin = isSpigotPlugin;
    }

    /**
     * Getter for property 'addons'.
     *
     * @return Value for property 'addons'.
     */
    public List<AtherialAddon> getAddons() {
        return new ArrayList<>(AtherialAddonManager.getManager().getAddonMap().values());
    }
}
