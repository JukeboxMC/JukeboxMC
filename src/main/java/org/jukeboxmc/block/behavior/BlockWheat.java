package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.data.BlockCrops;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWheat extends BlockCrops {

    public BlockWheat( Identifier identifier ) {
        super( identifier );
    }

    public BlockWheat( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        if ( !this.isFullyGrown() ) {
            return Collections.singletonList( Item.create( ItemType.WHEAT_SEEDS ) );
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = 0;
        int attempts = 3 + Math.min( 0, item.hasEnchantment( EnchantmentType.FORTUNE ) ? item.getEnchantment( EnchantmentType.FORTUNE ).getLevel() : 0 );
        for ( int i = 0; i < attempts; i++ ) {
            if ( random.nextInt( 7 ) < 4 ) {
                amount++;
            }
        }
        if ( amount > 0 ) {
            return Arrays.asList( Item.create( ItemType.WHEAT ), Item.create( ItemType.WHEAT_SEEDS ).setAmount( amount ) );
        } else {
            return Collections.singletonList( Item.create( ItemType.WHEAT ) );
        }
    }
}
