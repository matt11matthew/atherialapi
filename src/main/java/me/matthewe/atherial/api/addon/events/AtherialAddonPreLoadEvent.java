package me.matthewe.atherial.api.addon.events;

import me.matthewe.atherial.api.addon.AtherialAddon;
import me.matthewe.atherial.api.addon.event.AtherialEvent;
import me.matthewe.atherial.api.addon.event.Cancellable;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialAddonPreLoadEvent extends AtherialEvent implements Cancellable {
    private final AtherialAddon atherialAddon;
    private boolean isCancelled;

    public AtherialAddonPreLoadEvent(AtherialAddon atherialAddon) {
        this.atherialAddon = atherialAddon;
        this.isCancelled = false;
    }

    /**
     * Getter for property 'atherialAddon'.
     *
     * @return Value for property 'atherialAddon'.
     */
    public AtherialAddon getAtherialAddon() {
        return atherialAddon;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }
}
