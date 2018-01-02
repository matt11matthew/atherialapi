package me.matthewe.atherial.api.addon;


import me.matthewe.atherial.api.addon.event.AtherialEvent;
import me.matthewe.atherial.api.addon.event.AtherialEventHandler;
import me.matthewe.atherial.api.addon.event.AtherialEventListener;
import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialAddonManager {
    private static AtherialAddonManager instance;
    private static PluginManagerUtil pluginManager;
    private Map<String, AtherialAddon> atherialAddonMap;
    private Map<AtherialAddon, List<Class<? extends AtherialEventListener>>> atherialEventListeners;

    public AtherialAddonManager() {
        this.atherialAddonMap = new HashMap<>();
        this.atherialEventListeners = new HashMap<>();
        instance = this;
    }

    public static AtherialAddonManager getManager() {
        if (instance == null) {
            instance = new AtherialAddonManager();
        }
        return instance;
    }

    protected static AtherialAddonManager getInstance() {
        if (instance == null) {
            instance = new AtherialAddonManager();
        }
        return instance;
    }

    public void callEvent(final AtherialEvent event, AtherialAddon addon) {
        getHandlers(addon).stream().filter(handler -> Arrays.asList(handler.getInterfaces()).contains(AtherialEventListener.class)).forEach(handler -> {
            Method[] methods = handler.getMethods();
            Arrays.stream(methods).forEachOrdered(method -> {
                AtherialEventHandler eventHandler = method.getAnnotation(AtherialEventHandler.class);
                if (eventHandler != null) {
                    Class[] methodParams = method.getParameterTypes();
                    if (methodParams.length < 1 || !event.getClass().getSimpleName().equals(methodParams[0].getSimpleName()))
                        return;
                    try {
                        method.invoke(handler.newInstance(), event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    public List<Class<? extends AtherialEventListener>> getHandlers(AtherialAddon atherialAddon) {
        return this.atherialEventListeners.get(atherialAddon);
    }

    public void registerListener(AtherialEventListener listener, AtherialAddon atherialAddon) {
        if (atherialEventListeners.containsKey(atherialAddon)) {
            List<Class<? extends AtherialEventListener>> classes = atherialEventListeners.get(atherialAddon);
            if (!classes.contains(listener.getClass())) {
                classes.add(listener.getClass());
            }
            atherialEventListeners.remove(atherialAddon);
            atherialEventListeners.put(atherialAddon, classes);
        } else {
            List<Class<? extends AtherialEventListener>> classes = new ArrayList<>();
            classes.add(listener.getClass());
            atherialEventListeners.put(atherialAddon, classes);
        }
    }

    public void loadAddons(File file) {
        pluginManager = PluginManagerFactory.createPluginManagerX();
        System.out.println("[AtherialApi] Loading Addons");
        pluginManager.addPluginsFrom(file.toURI());

        for (Plugin plugin : pluginManager.getPlugins()) {
            if (plugin instanceof AtherialAddon) {
                AtherialAddon atherialAddon = (AtherialAddon) plugin;
                Class<? extends Plugin> aClass = plugin.getClass();
                if (aClass.isAnnotationPresent(AtherialAddonDescription.class)) {
                    AtherialAddonDescription annotation = aClass.getAnnotation(AtherialAddonDescription.class);
                    if (annotation != null) {
                        atherialAddon.setDescription(annotation);
                    }
                }
                atherialAddonMap.put(atherialAddon.getDescription().name(), atherialAddon);
            }
        }

        for (AtherialAddon atherialAddon : atherialAddonMap.values()) {
            atherialAddon.onEnable();
            System.out.println("[AtherialApi] Enabled " + atherialAddon.getDescription().name());
        }
    }

    public Map<String, AtherialAddon> getAddonMap() {
        return atherialAddonMap;
    }
}
