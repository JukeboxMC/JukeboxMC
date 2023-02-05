package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;
import org.jukeboxmc.world.generator.object.Ore;
import org.jukeboxmc.world.generator.object.OreType;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class OrePopulator extends Populator {

    private final OreType[] oreTypes;

    public OrePopulator( OreType[] oreTypes ) {
        this.oreTypes = oreTypes;
    }

    @Override
    public void populate( Random random, World world, PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        for ( OreType oreType : this.oreTypes ) {
            Ore ore = new Ore( random, oreType );
            for ( int i = 0; i < oreType.getClusterCount(); i++ ) {
                int x = random.nextInt( 15 ) + chunkX * 16;
                int y = random.nextInt( oreType.getMaxHeight() - oreType.getMinHeight() ) + oreType.getMinHeight();
                int z = random.nextInt( 15 ) + chunkZ * 16;

                if ( ore.canPlace( chunkManager, x, y, z ) ) {
                    ore.place( chunkManager, x, y, z );
                }
            }
        }
    }
}
