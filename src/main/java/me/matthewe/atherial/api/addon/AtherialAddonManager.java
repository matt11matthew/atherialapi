package me.matthewe.atherial.api.addon;

import me.matthewe.atherial.api.AtherialEventListener;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialAddonManager {
    private Map<String, AtherialAddon> atherialAddonMap;
    private Map<AtherialAddon, List<Class<? extends AtherialEventListener>>> atherialEventListeners;

    public static AtherialAddonManager getManager() {
        if (instance == null) {
            instance = new AtherialAddonManager();
        }
        return instance;
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

    private static AtherialAddonManager instance;

    protected static AtherialAddonManager getInstance() {
        if (instance == null) {
            instance = new AtherialAddonManager();
        }
        return instance;
    }

    public AtherialAddonManager() {
        this.atherialAddonMap = new HashMap<>();
        this.atherialEventListeners = new HashMap<>();
        instance = this;
    }

    public void loadAddons(File file) {
        System.out.println("[AtherialApi] Loading Addons");
        if (file != null && file.exists() && (file.isDirectory()) && (file.list() != null)) {
            for (File file1 : file.listFiles()) {
                if (file1.getName().endsWith(".jar")) {
                    List<Class> classes = loadClasses(file1);
                    for (Class aClass : classes) {
                        for (Annotation annotation : aClass.getAnnotations()) {
                            if (annotation instanceof AtherialAddonDescription) {
                                AtherialAddonDescription description = (AtherialAddonDescription) annotation;
                                try {
                                    AtherialAddon atherialAddon = (AtherialAddon) aClass.getDeclaredConstructor().newInstance();
                                    atherialAddon.setDescription(description);
                                    atherialAddonMap.put(description.name(), atherialAddon);
                                    System.out.println("[AtherialAddons] Loading " + description.name() + "...");
                                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        for (AtherialAddon atherialAddon : atherialAddonMap.values()) {
            atherialAddon.onLoad();
            System.out.println("[AtherialApi] Enabled " + atherialAddon.getDescription().name());
        }
    }

    private void loadAddon(File file, List<Class> classes) {
        for (Class aClass : classes) {
            if (aClass.isAnnotationPresent(AtherialAddonDescription.class) && aClass.isAssignableFrom(AtherialAddon.class)) {
                System.out.println("true");
                AtherialAddonDescription description = (AtherialAddonDescription) aClass.getAnnotation(AtherialAddonDescription.class);
                try {
                    AtherialAddon atherialAddon = (AtherialAddon) aClass.getDeclaredConstructor().newInstance();
                    atherialAddon.setDescription(description);
                    atherialAddonMap.put(description.name(), atherialAddon);
                    System.out.println("[AtherialAddons] " + description.name() + " loading...");
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private   List<Class> loadClasses(File file) {
        List<Class> classList= new ArrayList<>();
        try {
           JarFile jarFile = new JarFile(file);
           Enumeration<JarEntry> entries = jarFile.entries();

           URL[] urls = {new URL("jar:file:" + file.getPath() + "!/")};
           URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);
           while (entries.hasMoreElements()) {
               JarEntry jarEntry = entries.nextElement();
               if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                   continue;
               }
               String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
               className = className.replace('/', '.');
               Class aClass = urlClassLoader.loadClass(className);
               classList.add(aClass);
           }
       } catch (ClassNotFoundException | IOException e) {

       }
       return classList;
    }
}
