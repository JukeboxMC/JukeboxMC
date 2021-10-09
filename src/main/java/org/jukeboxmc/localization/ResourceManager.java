package org.jukeboxmc.localization;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ResourceManager {

    private final HashMap<Locale, String> loadedLocaleLoadStrings = new HashMap<>();

    private final HashMap<Locale, SoftReference<ResourceLoader>> loadedLocales = new HashMap<>();

    private final ArrayList<ResourceLoader> registerdLoaders = new ArrayList<>();

    private final Object sharedLock = new Object();

    private ClassLoader classLoader;

    public ResourceManager( ClassLoader classLoader ) {
        this.classLoader = classLoader;
    }

    public synchronized void registerLoader( ResourceLoader loader ) {
        synchronized (sharedLock) {
            registerdLoaders.add( loader );
        }
    }

    private synchronized void loadLocale( Locale locale, String param ) throws ResourceLoadFailedException {
        //Get the correct loader for this param
        for ( ResourceLoader loader : registerdLoaders ) {
            for ( String ending : loader.getFormats() ) {
                if ( param.endsWith( ending ) ) {
                    try {
                        synchronized ( sharedLock ) {
                            loadedLocales.put( locale, new SoftReference<>( buildNewResourceLoader( loader, param ) ) );
                            loadedLocaleLoadStrings.put( locale, param );
                        }
                    } catch ( RuntimeException e ) {
                        throw new ResourceLoadFailedException( e );
                    }
                }
            }
        }
    }

    public synchronized void load( Locale locale, String param ) throws ResourceLoadFailedException {
        //Check if locale has already been loaded
        if ( loadedLocales.containsKey( locale ) ) {
            synchronized ( sharedLock ) {
                //Unload the locale and get the new one
                ResourceLoader loader = loadedLocales.get( locale ).get();
                if ( loader != null ) {
                    loader.cleanup();
                }

                loadedLocales.remove( locale );
                loadedLocaleLoadStrings.remove( locale );
            }
        }

        loadLocale( locale, param );
    }

    private synchronized void reloadIfGCCleared( Locale locale ) throws ResourceLoadFailedException {
        if ( loadedLocales.get( locale ).get() == null ) {
            synchronized ( sharedLock ) {
                loadLocale( locale, loadedLocaleLoadStrings.get( locale ) );
            }
        }
    }

    public String get( Locale locale, String key ) throws ResourceLoadFailedException {
        //If Locale is not loaded throw a ResourceNotLoadedException
        if ( loadedLocales.containsKey( locale ) ) {
            //Check if this Locale contains the key searched for
            reloadIfGCCleared( locale );

            if ( loadedLocales.get( locale ).get().getKeys().contains( key ) ) {
                return loadedLocales.get( locale ).get().get( key );
            }
        }

        //Check if there is a Resource for the language only (so you can inherit en to en_US for example)
        Locale baseLocale = new Locale( locale.getLanguage() );

        //If Locale is not loaded throw a ResourceNotLoadedException
        if ( loadedLocales.containsKey( baseLocale ) ) {
            //Check if this Locale contains the key searched for
            reloadIfGCCleared( baseLocale );

            if ( loadedLocales.get( baseLocale ).get().getKeys().contains( key ) ) {
                return loadedLocales.get( baseLocale ).get().get( key );
            }
        }

        return null;
    }

    public boolean isLoaded( Locale locale ) {
        return loadedLocales.containsKey( locale );
    }

    private synchronized ResourceLoader buildNewResourceLoader( ResourceLoader loader, String argument ) {
        try {
            Constructor<?> constructor = loader.getClass().getConstructor( ClassLoader.class, String.class );
            return (ResourceLoader) constructor.newInstance( this.classLoader, argument );
        } catch ( NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e ) {
            throw new RuntimeException( "Could not construct new ResourceLoader", e );
        }
    }

    public synchronized void reload() {
        //Reload all ResourceLoaders
        for ( SoftReference<ResourceLoader> loader : loadedLocales.values() ) {
            try {
                if ( loader != null && loader.get() != null ) {
                    loader.get().reload();
                }
            } catch ( ResourceLoadFailedException e ) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void cleanup() {
        //Cleanup all ResourceLoaders
        for ( SoftReference<ResourceLoader> loader : loadedLocales.values() ) {
            if ( loader != null && loader.get() != null ) {
                loader.get().cleanup();
            }
        }

        //Remove all refs
        classLoader = null;
    }

    public List<Locale> getLoadedLocales() {
        return new ArrayList<>( loadedLocales.keySet() );
    }
}
