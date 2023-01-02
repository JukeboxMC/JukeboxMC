package org.jukeboxmc.plugin;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.CommandManager;
import org.jukeboxmc.event.*;
import org.jukeboxmc.logger.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author WaterdogPE
 * @version 1.0
 */

public class PluginManager {

    private final Logger logger;
    private final Server server;
    private final PluginLoader pluginLoader;
    private final CommandManager commandManager;

    private final Yaml yamlLoader = new Yaml( new CustomClassLoaderConstructor( this.getClass().getClassLoader() ) );
    private final Object2ObjectMap<String, Plugin> pluginMap = new Object2ObjectArrayMap<>();
    private final Object2ObjectMap<String, Class<?>> cachedClasses = new Object2ObjectArrayMap<>();
    final Object2ObjectMap<String, PluginClassLoader> pluginClassLoaders = new Object2ObjectArrayMap<>();
    final Map<Class<? extends Event>, Map<EventPriority, List<RegisteredListener>>> listeners = new HashMap<>();

    public PluginManager( Server server ) {
        this.server = server;
        this.logger = server.getLogger();
        this.pluginLoader = new PluginLoader( this.logger, this );
        this.commandManager = new CommandManager();
        this.loadPluginsIn( this.server.getPluginFolder().toPath() );
    }

    public void loadPluginsIn( Path folderPath ) {
        this.loadPluginsIn( folderPath, false );
    }

    public void loadPluginsIn( Path folderPath, boolean directStartup ) {
        try {
            Stream<Path> pluginPaths = Files.walk( folderPath );
            pluginPaths.filter( Files::isRegularFile )
                    .filter( PluginLoader::isJarFile )
                    .forEach( jarPath -> this.loadPlugin( jarPath, directStartup ) );
        } catch ( IOException e ) {
            this.logger.error( "Error while filtering plugin files " + e );
        }
    }

    public Plugin loadPlugin( Path path ) {
        return this.loadPlugin( path, false );
    }

    public Plugin loadPlugin( Path path, boolean directStartup ) {
        if ( !Files.isRegularFile( path ) || !PluginLoader.isJarFile( path ) ) {
            this.logger.warn( "Cannot load plugin: Provided file is no jar file: " + path.getFileName() );
            return null;
        }

        File pluginFile = path.toFile();
        if ( !pluginFile.exists() ) {
            return null;
        }

        PluginYAML config = this.pluginLoader.loadPluginData( pluginFile, this.yamlLoader );
        if ( config == null ) {
            return null;
        }

        if ( this.getPluginByName( config.getName() ) != null ) {
            this.logger.warn( "Plugin is already loaded: " + config.getName() );
            return null;
        }

        Plugin plugin = this.pluginLoader.loadPluginJAR( config, pluginFile );
        if ( plugin == null ) {
            return null;
        }

        this.pluginMap.put( config.getName(), plugin );

        plugin.onStartup();
        if ( directStartup ) {
            try {
                plugin.setEnabled( true );
            } catch ( Exception e ) {
                this.logger.error( "Direct startup failed!" + e.getMessage() );
            }
        }
        return plugin;
    }

    public void enableAllPlugins( PluginLoadOrder pluginLoadOrder ) {
        LinkedList<Plugin> failed = new LinkedList<>();

        for ( Plugin plugin : this.pluginMap.values() ) {
            if ( plugin.getLoadOrder().equals( pluginLoadOrder ) ) {
                if ( !this.enablePlugin( plugin, null ) ) {
                    failed.add( plugin );
                }
            }
        }

        if ( !failed.isEmpty() ) {
            StringBuilder builder = new StringBuilder( "§cFailed to load plugins: §e" );
            while ( failed.peek() != null ) {
                Plugin plugin = failed.poll();
                builder.append( plugin.getName() );
                if ( failed.peek() != null ) {
                    builder.append( ", " );
                }
            }
            this.logger.info( builder.toString() );
        }
    }

    public boolean enablePlugin( Plugin plugin, String parent ) {
        if ( plugin.isEnabled() ) return true;
        String pluginName = plugin.getName();

        if ( plugin.getDescription().getDepends() != null ) {
            for ( String depend : plugin.getDescription().getDepends() ) {
                if ( depend.equals( parent ) ) {
                    this.logger.warn( "§cCan not enable plugin " + pluginName + " circular dependency " + parent + "!" );
                    return false;
                }

                Plugin dependPlugin = this.getPluginByName( depend );
                if ( dependPlugin == null ) {
                    this.logger.warn( "§cCan not enable plugin " + pluginName + " missing dependency " + depend + "!" );
                    return false;
                }

                if ( !dependPlugin.isEnabled() && !this.enablePlugin( dependPlugin, pluginName ) ) {
                    return false;
                }
            }
        }

        try {
            plugin.setEnabled( true );
            this.logger.info( "Loaded plugin " + plugin.getName() + " Version: " + plugin.getVersion() + " successfully!" );
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void disableAllPlugins() {
        for ( Plugin plugin : this.pluginMap.values() ) {
            this.logger.info( "Disabling plugin " + plugin.getName() + "" );
            try {
                plugin.setEnabled( false );
            } catch ( RuntimeException e ) {
                e.printStackTrace();
            }
        }
    }

    Class<?> getClassFromCache( String className ) {
        Class<?> clazz = this.cachedClasses.get( className );
        if ( clazz != null ) {
            return clazz;
        }

        for ( PluginClassLoader loader : this.pluginClassLoaders.values() ) {
            try {
                if ( ( clazz = loader.findClass( className, false ) ) != null ) {
                    return clazz;
                }
            } catch ( ClassNotFoundException e ) {
                //ignore
            }
        }
        return null;
    }

    protected void cacheClass( String className, Class<?> clazz ) {
        this.cachedClasses.putIfAbsent( className, clazz );
    }

    private Map<String, Plugin> getPluginMap() {
        return this.pluginMap;
    }

    public Collection<Plugin> getPlugins() {
        return this.pluginMap.values();
    }

    public Plugin getPluginByName( String pluginName ) {
        return this.pluginMap.getOrDefault( pluginName, null );
    }

    public Server getServer() {
        return this.server;
    }

    public void registerListener( Listener listener ) {
        Class<? extends Listener> listenerClass = listener.getClass();
        for ( Method method : listenerClass.getDeclaredMethods() ) {
            EventHandler eventHandler = method.getAnnotation( EventHandler.class );
            if ( eventHandler == null ) {
                continue;
            }
            EventPriority eventPriority = eventHandler.priority();
            if ( method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom( method.getParameterTypes()[0] ) ) {
                continue;
            }
            Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
            this.listeners.putIfAbsent( eventClass, new LinkedHashMap<>() );
            this.listeners.get( eventClass ).putIfAbsent( eventPriority, new ArrayList<>() );
            this.listeners.get( eventClass ).get( eventPriority ).add( new RegisteredListener( method, listener ) );
        }
    }

    public void callEvent( Event event ) {
        Map<EventPriority, List<RegisteredListener>> eventPriorityListMap = this.listeners.get( event.getClass() );
        if ( eventPriorityListMap != null ) {
            for ( EventPriority eventPriority : EventPriority.values() ) {
                List<RegisteredListener> methods = eventPriorityListMap.get( eventPriority );
                if ( methods != null ) {
                    for ( RegisteredListener registeredListener : methods ) {
                        try {
                            registeredListener.getMethod().invoke( registeredListener.getListener(), event );
                        } catch ( IllegalAccessException | InvocationTargetException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }
}
