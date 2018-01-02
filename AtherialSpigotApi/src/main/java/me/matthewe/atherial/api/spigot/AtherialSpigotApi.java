package me.matthewe.atherial.api.spigot;

import me.matthewe.atherial.api.AtherialApi;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AtherialSpigotApi extends JavaPlugin {
    private static AtherialSpigotApi instance;

    /**
     * Getter for property 'instance'.
     *
     * @return Value for property 'instance'.
     */
    public static AtherialSpigotApi getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        instance = this;
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        File file = new File(this.getDataFolder() + "/addons");
        if (!file.exists()) {
            file.mkdirs();
        }

        AtherialApi atherialApi = AtherialApi.getInstance();
        atherialApi.setSpigotPlugin(true);
        atherialApi.loadAddons(file);


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

 