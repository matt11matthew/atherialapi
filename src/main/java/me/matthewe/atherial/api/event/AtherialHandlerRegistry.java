package me.matthewe.atherial.api.event;

import me.matthewe.atherial.api.AtherialEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialHandlerRegistry {
    private static List<Class<? extends AtherialEventListener>> handlers = new ArrayList<>();

    public static List<Class<? extends AtherialEventListener>> getHandlers() {
        return handlers;
    }

    public static void registerListener(AtherialEventListener  listener) {
        handlers.add(listener.getClass());

    }
}
