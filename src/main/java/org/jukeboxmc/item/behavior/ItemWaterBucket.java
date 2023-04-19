package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaterBucket extends ItemBucket {

    public ItemWaterBucket( Identifier identifier ) {
        super( identifier );
    }

    public ItemWaterBucket( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public Block toBlock() {
        return Block.create( BlockType.WATER );
    }
}
