package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLavaBucket extends ItemBucket implements Burnable {
    
    public ItemLavaBucket( Identifier identifier ) {
        super( identifier );
    }

    public ItemLavaBucket( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public Block toBlock() {
        return Block.create( BlockType.LAVA );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 20000 );
    }
}
