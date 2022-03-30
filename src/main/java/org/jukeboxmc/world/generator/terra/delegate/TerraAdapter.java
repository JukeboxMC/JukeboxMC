package org.jukeboxmc.world.generator.terra.delegate;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.world.Biome;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TerraAdapter {

    private static final Map<Block, BlockStateDelegate> stateDelegateStore = new WeakHashMap<>( 2820, 0.99f );

    public static TerraItemDelegate adapt( Item item ) {
        return new TerraItemDelegate( item );
    }

    public static TerraBiomesDelegate adapt( Biome biome ) {
        return new TerraBiomesDelegate( biome );
    }

    public static BlockStateDelegate adapt( Block blockState ) {
        if ( stateDelegateStore.containsKey( blockState ) ) {
            return stateDelegateStore.get( blockState );
        }
        final var delegate = new BlockStateDelegate( blockState );
        stateDelegateStore.put( blockState, delegate );
        return delegate;
    }
}
