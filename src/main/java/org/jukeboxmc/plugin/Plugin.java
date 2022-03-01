package org.jukeboxmc.plugin;

import com.google.common.base.Preconditions;
import lombok.NoArgsConstructor;
import org.jukeboxmc.Server;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author WaterdogPE
 * @version 1.0
 */
@NoArgsConstructor
public abstract class Plugin {

    protected boolean enabled = false;
    private PluginYAML description;
    private Server server;
    private File pluginFile;
    private File dataFolder;
    private Logger logger;
    private boolean initialized = false;

    protected final void init( PluginYAML description, Server server, File pluginFile ) {
        Preconditions.checkArgument( !this.initialized, "Plugin has been already initialized!" );
        this.initialized = true;
        this.description = description;
        this.server = server;
        this.logger = server.getLogger();

        this.pluginFile = pluginFile;
        this.dataFolder = new File( server.getPluginFolder() + "/" + description.getName() + "/" );
        if ( !this.dataFolder.exists() ) {
            this.dataFolder.mkdirs();
        }
    }

    /**
     * Called when the plugin is loaded into the server, but before it was enabled.
     * Can be used to load important information or to establish connections
     */
    public void onStartup() {
    }

    /**
     * Called when the base server startup is done and the plugins are getting enabled.
     * Also called whenever the plugin state changes to enabled
     */
    public abstract void onEnable();

    /**
     * Called on server shutdown, or when the plugin gets disabled, for example by another plugin or when an error occurred.
     * Also gets called when the plugin state changes to disabled
     */
    public void onDisable() {
    }

    /**
     * @param filename the file name to read
     * @return Returns a file from inside the plugin jar as an InputStream
     */
    public InputStream getResourceFile( String filename ) {
        try {
            JarFile pluginJar = new JarFile( this.pluginFile );
            JarEntry entry = pluginJar.getJarEntry( filename );
            return pluginJar.getInputStream( entry );
        } catch ( IOException e ) {
            this.logger.error( "Can not get plugin resource!" );
        }
        return null;
    }

    public boolean saveResource( String filename ) {
        return this.saveResource( filename, false );
    }

    public boolean saveResource( String filename, boolean replace ) {
        return this.saveResource( filename, filename, replace );
    }

    /**
     * Saves a resource from the plugin jar's resources to the plugin folder
     *
     * @param filename   the name of the file in the jar's resources
     * @param outputName the name the file should be saved as in the plugin folder
     * @param replace    whether the file should be replaced even if present already
     * @return returns false if an exception occurred, the file already exists and shouldn't be replaced, and when the file could
     * not be found in the jar
     * returns true if the file overwrite / copy was successful
     */
    public boolean saveResource( String filename, String outputName, boolean replace ) {
        Preconditions.checkArgument( filename != null && !filename.trim().isEmpty(), "Filename can not be null!" );

        File file = new File( this.dataFolder, outputName );
        if ( file.exists() && !replace ) {
            return false;
        }

        try ( InputStream resource = this.getResourceFile( filename ) ) {
            if ( resource == null ) {
                return false;
            }
            File outFolder = file.getParentFile();
            if ( !outFolder.exists() ) {
                outFolder.mkdirs();
            }
            Utils.writeFile( file, resource );
        } catch ( IOException e ) {
            this.logger.error( "Can not save plugin file!" );
            return false;
        }
        return true;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Changes the plugin's state
     *
     * @param enabled whether the plugin should be enabled or disabled
     * @throws RuntimeException Thrown whenever an uncaught error occurred in onEnable() or onDisable() of a plugin
     */
    public void setEnabled( boolean enabled ) throws RuntimeException {
        if ( this.enabled == enabled ) {
            return;
        }
        this.enabled = enabled;
        try {
            if ( enabled ) {
                this.onEnable();
            } else {
                this.onDisable();
            }
        } catch ( Exception e ) {
            throw new RuntimeException( "Can not " + ( enabled ? "enable" : "disable" ) + " plugin " + this.description.getName() + "!", e );
        }
    }

    public PluginYAML getDescription() {
        return this.description;
    }

    public String getName() {
        return this.description.getName();
    }

    public String getVersion() {
        return this.description.getVersion();
    }

    public String getAuthor() {
        return this.description.getAuthor();
    }

    public List<String> getAuthors() {
        return this.description.getAuthors();
    }

    public PluginLoadOrder getLoadOrder() {
        return this.description.getLoadOrder();
    }

    public Server getServer() {
        return this.server;
    }

    public File getDataFolder() {
        return this.dataFolder;
    }

    public Logger getLogger() {
        return this.logger;
    }
}
