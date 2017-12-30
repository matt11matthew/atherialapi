package me.matthewe.atherial.api.event;

import me.matthewe.atherial.api.AtherialEventListener;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialEventDispatcher {

    public void callEvent(final AtherialEvent event) {
        call(event);
    }

    private void call(final AtherialEvent event) {
        for (Class handler : AtherialHandlerRegistry.getHandlers()) {
            if (Arrays.asList(handler.getInterfaces()).contains(AtherialEventListener.class)) {


                Method[] methods = handler.getMethods();

                for (int i = 0; i < methods.length; ++i) {

                    AtherialEventHandler eventHandler = methods[i].getAnnotation(AtherialEventHandler.class);
                    if (eventHandler != null) {
                        Class[] methodParams = methods[i].getParameterTypes();

                        if (methodParams.length < 1)
                            continue;

                        if (!event.getClass().getSimpleName()
                                .equals(methodParams[0].getSimpleName()))
                            continue;

                        // defence from runtime exceptions:
                        try {
                            methods[i].invoke(handler.newInstance(), event);
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                }
            }
        }
    }
}