package me.matthewe.atherial.api.addon;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.events.Shutdown;

/**
 * Created by Matthew E on 12/30/2017.
 */
@PluginImplementation
public abstract class AtherialAddon implements Plugin {
    private AtherialAddonDescription description;

    @Init
    public void onEnable() {
    }

    @Shutdown
    public void onDisable() {
    }


    /**
     * Getter for property 'description'.
     *
     * @return Value for property 'description'.
     */
    public AtherialAddonDescription getDescription() {
        return description;
    }

    /**
     * Setter for property 'description'.
     *
     * @param description Value to set for property 'description'.
     */
    protected void setDescription(AtherialAddonDescription description) {
        this.description = description;
    }
}
