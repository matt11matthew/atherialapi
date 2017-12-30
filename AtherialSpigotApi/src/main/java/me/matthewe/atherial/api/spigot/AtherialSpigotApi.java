package me.matthewe.atherial.api.spigot;

import me.matthewe.atherial.api.AtherialApi;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AtherialSpigotApi extends JavaPlugin {

    @Override
    public void onEnable() {
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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
