package org.jukeboxmc.world.generator.terra;

import com.dfsek.tectonic.api.TypeRegistry;
import com.dfsek.terra.AbstractPlatform;
import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.event.events.platform.PlatformInitializationEvent;
import com.dfsek.terra.api.handle.ItemHandle;
import com.dfsek.terra.api.handle.WorldHandle;
import com.dfsek.terra.api.registry.key.RegistryKey;
import com.dfsek.terra.api.world.biome.PlatformBiome;
import com.dfsek.terra.config.pack.ConfigPackImpl;
import org.jukeboxmc.Server;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.generator.terra.delegate.TerraAdapter;
import org.jukeboxmc.world.generator.terra.delegate.TerraBiomesDelegate;
import org.jukeboxmc.world.generator.terra.handles.TerraItemHandle;
import org.jukeboxmc.world.generator.terra.handles.TerraWorldHandle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.zip.ZipFile;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TerraPlatform extends AbstractPlatform {

    public static final File DATA_PATH;
    private static TerraPlatform INSTANCE = null;

    private static final TerraWorldHandle terraWorldHandle = new TerraWorldHandle();
    private static final TerraItemHandle terraItemHandle = new TerraItemHandle();

    static {
        DATA_PATH = new File( "./terra" );
        if ( !DATA_PATH.exists() ) {
            if ( !DATA_PATH.mkdirs() ) {
                Server.getInstance().getLogger().info( "Failed to create terra config folder." );
            }
        }
        var targetFile = new File( "./terra/config.yml" );
        if ( !targetFile.exists() ) {
            var terraDefaultConfigStream = Server.class.getClassLoader().getResourceAsStream( "terra_default_config.yml" );
            if ( terraDefaultConfigStream != null ) {
                try {
                    Files.copy( terraDefaultConfigStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING );
                } catch ( IOException e ) {
                    Server.getInstance().getLogger().info( "Failed to extract terra config." );
                }
            } else {
                Server.getInstance().getLogger().info( "Failed to extract terra config." );
            }
        }
    }

    public synchronized static TerraPlatform getInstance() {
        if ( INSTANCE != null ) {
            return INSTANCE;
        }
        final var platform = new TerraPlatform();
        platform.load();
        platform.getEventManager().callEvent( new PlatformInitializationEvent() );
        final var configRegistry = platform.getConfigRegistry();
        final var packsDir = new File( "./terra/packs" );
        for ( final var each : Objects.requireNonNull( packsDir.listFiles() ) ) {
            if ( each.isFile() && each.getName().endsWith( ".zip" ) ) {
                try {
                    final var configFile = new ZipFile( each );
                    final var configPack = new ConfigPackImpl( configFile, platform );
                    var packName = each.getName();
                    packName = packName.substring( Math.max( packName.lastIndexOf( "/" ), packName.lastIndexOf( "\\" ) ) + 1,
                            packName.lastIndexOf( "." ) );
                    configRegistry.register( RegistryKey.of( "TerraGenerator", packName ), configPack );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        if ( TerraWorldHandle.err != 0 ) {
            Server.getInstance().getLogger().warn( "Fail to load " + TerraWorldHandle.err + " terra block states." );
        }
        INSTANCE = platform;
        return platform;
    }


    @Override
    public boolean reload() {
        this.getTerraConfig().load( this );
        this.getRawConfigRegistry().clear();
        return this.getRawConfigRegistry().loadAll( this );
    }

    @Override
    public void runPossiblyUnsafeTask(Runnable task) {
        Server.getInstance().getScheduler().execute(task);
    }

    @Override
    public String platformName() {
        return "JukeboxMC";
    }

    @Override
    public WorldHandle getWorldHandle() {
        return terraWorldHandle;
    }

    @Override
    public File getDataFolder() {
        return DATA_PATH;
    }

    @Override
    public ItemHandle getItemHandle() {
        return terraItemHandle;
    }

    @Override
    public void register( TypeRegistry registry ) {
        super.register( registry );
        registry.registerLoader( PlatformBiome.class, ( type, o, loader, depthTracker ) -> parseBiome( (String) o ) )
                .registerLoader( BlockState.class, ( type, o, loader, depthTracker ) -> terraWorldHandle.createBlockState( (String) o ) );
    }

    private static TerraBiomesDelegate parseBiome( String str ) {
        if ( str.startsWith( "minecraft:" ) ) str = str.substring( 10 );
        int id = BiomeLegacyId2StringIdMap.INSTANCE.string2Legacy( str );
        Biome biome = Biome.findById( id );
        return TerraAdapter.adapt( biome == null ? Biome.PLAINS : biome );
    }
}
