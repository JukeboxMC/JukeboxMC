package org.jukeboxmc.command.internal;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.generator.biome.BiomeGrid;
import org.jukeboxmc.world.generator.biomegrid.MapLayer;

import java.util.stream.Stream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "locate" )
@Description ( "Locate a biome" )
public class LocateCommand extends Command {

    private final MapLayer[] biomeGrid;

    public LocateCommand() {
        super( CommandData.builder()
                .setParameters( new CommandParameter[]{
                        new CommandParameter( "biome", Stream.of( Biome.values() ).map( biome -> biome.name().toLowerCase() ).toList(), false )
                } )
                .build() );
        this.biomeGrid = MapLayer.initialize( 34465783, Dimension.OVERWORLD, 1 );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        int radius = 5000;

        if ( commandSender instanceof Player player ) {
            for ( int chunkX = player.getChunkX() + radius; chunkX > player.getChunkX() - radius; chunkX-- ) {
                for ( int chunkZ = player.getChunkZ() + radius; chunkZ > player.getChunkZ() - radius; chunkZ-- ) {
                    BiomeGrid biomes = new BiomeGrid();
                    int[] biomeValues = this.biomeGrid[0].generateValues( chunkX << 4, chunkZ << 4, 16, 16 );
                    for ( int i = 0; i < biomeValues.length; i++ ) {
                        biomes.biomes[i] = (byte) biomeValues[i];
                    }
                    for ( int sx = 0; sx < 16; sx++ ) {
                        for ( int sz = 0; sz < 16; sz++ ) {
                            Biome biome = biomes.getBiome( sx, sz );
                            if ( biome == Biome.valueOf( args[0].toUpperCase() ) ) {
                                int x = chunkX * 16;
                                int z = chunkZ * 16;
                                player.teleport( new Location( player.getWorld(), x, 100, z ) );
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
