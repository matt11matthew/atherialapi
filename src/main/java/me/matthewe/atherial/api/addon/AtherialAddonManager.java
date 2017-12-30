package me.matthewe.atherial.api.addon;

import me.matthewe.atherial.api.addon.event.AtherialEvent;
import me.matthewe.atherial.api.addon.event.AtherialEventHandler;
import me.matthewe.atherial.api.addon.event.AtherialEventListener;
import me.matthewe.atherial.api.addon.events.AtherialAddonPreLoadEvent;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialAddonManager {
    private Map<String, AtherialAddon> atherialAddonMap;
    private Map<String, List<Class>> stringClassMap;
    private Map<AtherialAddon, List<Class<? extends AtherialEventListener>>> atherialEventListeners;

    public static AtherialAddonManager getManager() {
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
        this.stringClassMap = new HashMap<>();
        instance = this;
    }

    public void loadAddons(File file, URL[] urls) {
        System.out.println("[AtherialApi] Loading Addons");
        if (file != null && file.exists() && (file.isDirectory()) && (file.list() != null)) {
            List<Class> classStream = Arrays.stream(file.listFiles()).filter(file1 -> file1.getName().endsWith(".jar")).map(file1 -> loadClasses(file1, false, urls)).flatMap(Collection::stream).collect(Collectors.toList());
            classStream  .forEach(aClass -> {
                Annotation annotation = aClass.getAnnotation(AtherialAddonDescription.class);
                if (annotation != null && annotation instanceof AtherialAddonDescription) {
                    AtherialAddonDescription description = (AtherialAddonDescription) annotation;
                    try {
                        String name = description.name();

                        stringClassMap.put(name, classStream);
                        AtherialAddon atherialAddon = (AtherialAddon) aClass.getDeclaredConstructor().newInstance();
                        atherialAddon.setDescription(description);
                        atherialAddonMap.put(name, atherialAddon);
                        System.out.println("[AtherialAddons] Loading " + name + "...");
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        for (AtherialAddon atherialAddon : atherialAddonMap.values()) {
            AtherialAddonPreLoadEvent atherialAddonPreLoadEvent = new AtherialAddonPreLoadEvent(atherialAddon);
            callEvent(atherialAddonPreLoadEvent, atherialAddon);
            if (atherialAddonPreLoadEvent.isCancelled()) {
                continue;
            }
            atherialAddon.onLoad();
            System.out.println("[AtherialApi] Enabled " + atherialAddon.getDescription().name());
        }
    }


    private  List<Class> loadClasses(File file, boolean debug, URL[] urls1) {
        List<Class> classList= new ArrayList<>();
        try {
           JarFile jarFile = new JarFile(file);
           Enumeration<JarEntry> entries = jarFile.entries();
           List<URL> urlList = Arrays.asList(new URL("jar:file:" + file.getPath() + "!/"));
            for (URL url : urls1) {
                urlList.add(url);
            }
            URL[] urls = new URL[urlList.size()];
            for (int i = 0; i < urls.length; i++) {
                urls[i] = urlList.get(i);
            }
//           URL[] urls = {new URL("jar:file:" + file.getPath() + "!/")};

           AddonClassLoader urlClassLoader = new AddonClassLoader(urls);
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
            if (debug) {
                e.printStackTrace();
            }
       }

       return classList;
    }
}
