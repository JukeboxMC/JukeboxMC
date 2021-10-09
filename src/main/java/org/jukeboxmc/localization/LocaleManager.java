package org.jukeboxmc.localization;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.localization.loader.PropertiesResourceLoader;
import org.jukeboxmc.localization.loader.YamlResourceLoader;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LocaleManager {

    private ResourceManager resourceManager;

    @Getter
    private Locale defaultLocale = Locale.US;

    @Getter
    @Setter
    private boolean useDefaultLocaleForMessages = true;

    public LocaleManager(Class<?> clazz) {
        this.resourceManager = new ResourceManager( clazz.getClassLoader() );
        this.resourceManager.registerLoader( new PropertiesResourceLoader() );
        this.resourceManager.registerLoader( new YamlResourceLoader() );

        try {
            URL jarFile = clazz.getProtectionDomain().getCodeSource().getLocation();
            String filePath = jarFile.toExternalForm();
            if ( filePath.startsWith( "file:/" ) ) {
                try ( JarFile openJarFile = new JarFile( filePath.substring( 6 ) ) ) {
                    Enumeration<JarEntry> jarEntryEnumeration = openJarFile.entries();
                    while ( jarEntryEnumeration.hasMoreElements() ) {
                        JarEntry entry = jarEntryEnumeration.nextElement();
                        if ( !entry.isDirectory() ) {
                            // We for sure don't support .class locales
                            String name = entry.getName();
                            if ( !name.endsWith( ".class" ) ) {
                                // remove file ending
                                String[] folderSplit = name.split( "/" );
                                String last = folderSplit[folderSplit.length - 1];
                                int lastDotIndex = last.lastIndexOf( '.' );
                                last = last.substring( 0, lastDotIndex );

                                if ( !last.contains( "_" ) ) {
                                    continue;
                                }

                                String[] localeSplit = last.split( "_" );
                                if ( localeSplit.length != 2 ) {
                                    continue;
                                }

                                Locale locale = new Locale( localeSplit[0], localeSplit[1] );
                                load( locale, name );
                            }
                        }
                    }
                }
            }
        } catch ( Exception e ) {
            // Ignore
        }
    }

    public List<Locale> getAvailableLocales( File path ) {
        File[] files = path.listFiles();
        if ( files == null ) return null;

        List<Locale> supported = new ArrayList<>();
        for ( File file : files ) {
            String[] locale = file.getName().substring( 0, 5 ).split( "_" );
            supported.add( new Locale( locale[0], locale[1] ) );
        }

        return supported;
    }

    public void initFromLocaleFolder( final File path ) {
        initFromLocaleFolderWithoutAutorefresh( path );

        new Timer().schedule( new TimerTask() {
            @Override
            public void run() {
                initFromLocaleFolderWithoutAutorefresh( path );
            }
        }, 0, 20 * 60 * 5 );
    }

    public void initFromLocaleFolderWithoutAutorefresh( File path ) {
        File[] files = path.listFiles();
        if ( files == null ) return;

        for ( File file : files ) {
            String[] locale = file.getName().substring( 0, 5 ).split( "_" );

            try {
                load( new Locale( locale[0], locale[1] ), "file://" + file.getAbsolutePath() );
            } catch ( ResourceLoadFailedException e ) {
                System.out.println( "Could not load i18n file " + file.getAbsolutePath() );
            }
        }
    }

    public synchronized void load( Locale locale, String param ) throws ResourceLoadFailedException {
        resourceManager.load( locale, param );
    }

    private String getTranslationString( Locale locale, String key ) throws ResourceLoadFailedException {
        return resourceManager.get( locale, key );
    }

    private Locale checkForDefault( Locale locale ) {
        if ( !resourceManager.isLoaded( locale ) ) {
            return defaultLocale;
        }

        return locale;
    }

    public void setDefaultLocale( Locale locale ) {
        defaultLocale = locale;
    }

    public String translate( String locale, String translationKey, Object... args ) {
        //Get the resource and translate
        Locale playerLocale = checkForDefault( Locale.forLanguageTag( locale.replace( "_", "-" ) ) );

        String translationString = null;
        try {
            translationString = getTranslationString( playerLocale, translationKey );
        } catch ( ResourceLoadFailedException e ) {
            try {
                translationString = getTranslationString( playerLocale = defaultLocale, translationKey );
            } catch ( ResourceLoadFailedException e1 ) {
                // Ignore .-.
            }
        }

        // Check for untranslated messages
        if ( translationString == null ) {
            return "N/A (" + translationKey + ")";
        }

        MessageFormat msgFormat = new MessageFormat( translationString );
        msgFormat.setLocale( playerLocale );
        return msgFormat.format( args );
    }

    public String translate( Locale locale, String translationKey, Object... args ) {
        //Get the resource and translate
        Locale playerLocale = checkForDefault( locale );

        String translationString = null;
        try {
            translationString = getTranslationString( playerLocale, translationKey );
        } catch ( ResourceLoadFailedException e ) {
            try {
                translationString = getTranslationString( playerLocale = defaultLocale, translationKey );
            } catch ( ResourceLoadFailedException e1 ) {
                // Ignore .-.
            }
        }

        // Check for untranslated messages
        if ( translationString == null ) {
            return "N/A (" + translationKey + ")";
        }

        MessageFormat msgFormat = new MessageFormat( translationString );
        msgFormat.setLocale( playerLocale );
        return msgFormat.format( args );
    }

    public String translate( String translationKey, Object... args ) {
        //Get the resource and translate
        String translationString = null;
        try {
            translationString = getTranslationString( defaultLocale, translationKey );
        } catch ( ResourceLoadFailedException e ) {
            // Ignore .-.
        }

        if ( translationString == null ) {
            System.out.println( "The key (" + translationKey + ") is not present in the Locale " + defaultLocale );
            return "N/A (" + translationKey + ")";
        }

        MessageFormat msgFormat = new MessageFormat( translationString );
        msgFormat.setLocale( defaultLocale );
        return msgFormat.format( args );
    }

    public void registerLoader( ResourceLoader loader ) {
        resourceManager.registerLoader( loader );
    }

    public List<Locale> getLoadedLocales() {
        return Collections.unmodifiableList( resourceManager.getLoadedLocales() );
    }

    public synchronized void reload() {
        resourceManager.reload();
    }

    public synchronized void cleanup() {
        resourceManager.cleanup();
        resourceManager = null;
    }
}
