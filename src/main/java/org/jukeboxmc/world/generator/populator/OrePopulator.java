package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.object.Ore;
import org.jukeboxmc.world.generator.object.OreType;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class OrePopulator implements Populator {

    private final OreType[] oreTypes;

    public OrePopulator(OreType[] oreTypes) {
        this.oreTypes = oreTypes;
    }

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        for ( OreType type : this.oreTypes ) {
            Ore ore = new Ore( random, type );
            for ( int i = 0; i < type.getClusterCount(); i++ ) {
                int x = random.nextInt( 15 ) + chunk.getChunkX() * 16;
                int y = random.nextInt( type.getMaxHeight() - type.getMinHeight() ) + type.getMinHeight();
                int z = random.nextInt( 15 ) + chunk.getChunkZ() * 16;

                if ( ore.canPlaceObject( chunk, x, y, z ) ) {
                    ore.placeObject( chunk, x, y, z );
                }
            }
        }
    }
}
