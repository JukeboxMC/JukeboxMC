package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPumpkinSeeds extends Item {

    public ItemPumpkinSeeds( Identifier identifier ) {
        super( identifier );
    }

    public ItemPumpkinSeeds( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public Block toBlock() {
        return Block.create( BlockType.PUMPKIN_STEM );
    }
}
