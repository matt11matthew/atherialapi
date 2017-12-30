package me.matthewe.atherial.api.event;

/**
 * Created by Matthew E on 12/30/2017.
 */
public interface Cancellable {
    boolean isCancelled();
    void setCancelled(boolean b);
}

