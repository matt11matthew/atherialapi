package me.matthewe.atherial.api.spigot;

import me.matthewe.atherial.api.AtherialApi;
import me.matthewe.atherial.api.spigot.command.SpigotAtherialCommandHandler;
import me.matthewe.atherial.api.spigot.commands.AddonsCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AtherialSpigotApi extends JavaPlugin {
    private static AtherialSpigotApi instance;
    private static SpigotAtherialCommandHandler atherialCommandHandler;

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

        atherialCommandHandler = new SpigotAtherialCommandHandler();
        atherialCommandHandler.registerCommand(new AddonsCommand());
        atherialCommandHandler.registerCommands();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

