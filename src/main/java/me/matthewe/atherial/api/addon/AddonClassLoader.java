package me.matthewe.atherial.api.addon;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AddonClassLoader extends URLClassLoader {

    private static final Set<AddonClassLoader> allLoaders = new CopyOnWriteArraySet<>();

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public AddonClassLoader(URL[] urls) {
        super(urls);
        allLoaders.add(this);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return loadClass0(name, resolve, true);
    }

    private Class<?> loadClass0(String name, boolean resolve, boolean checkOther) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException ex) {
        }
        if (checkOther) {
            for (AddonClassLoader loader : allLoaders) {
                if (loader != this) {
                    try {
                        return loader.loadClass0(name, resolve, false);
                    } catch (ClassNotFoundException ex) {
                    }
                }
            }
        }
        throw new ClassNotFoundException(name);
    }
}