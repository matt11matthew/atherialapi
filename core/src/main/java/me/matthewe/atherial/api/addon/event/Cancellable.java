package me.matthewe.atherial.api.addon.event;

/**
 * Created by Matthew E on 12/30/2017.
 */
public interface Cancellable {
    /**
     * Getter for property 'cancelled'.
     *
     * @return Value for property 'cancelled'.
     */
    boolean isCancelled();

    /**
     * Setter for property 'cancelled'.
     *
     * @param b Value to set for property 'cancelled'.
     */
    void setCancelled(boolean b);
}

