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
public class BlockPotatoes extends BlockCrops {

    public BlockPotatoes( Identifier identifier ) {
        super( identifier );
    }

    public BlockPotatoes( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        if ( !this.isFullyGrown() ) {
            return Collections.singletonList( Item.create( ItemType.POTATO ) );
        }
        int amount = 2;
        int attempts = 3 + Math.min( 0, item.hasEnchantment( EnchantmentType.FORTUNE ) ? item.getEnchantment( EnchantmentType.FORTUNE ).getLevel() : 0 );
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for ( int i = 0; i < attempts; i++ ) {
            if ( random.nextInt(7) < 4 ) {
                amount++;
            }
        }

        if ( random.nextInt(5) < 1 ) {
            return Arrays.asList( Item.create( ItemType.POTATO ), Item.create( ItemType.POISONOUS_POTATO ) );
        } else {
            return Collections.singletonList( Item.create( ItemType.POTATO ).setAmount( amount ) );
        }
    }
}
