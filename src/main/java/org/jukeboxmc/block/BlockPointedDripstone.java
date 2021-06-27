package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPointedDripstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPointedDripstone extends Block {

    public BlockPointedDripstone() {
        super( "minecraft:pointed_dripstone" );
    }

    @Override
    public ItemPointedDripstone toItem() {
        return new ItemPointedDripstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POINTED_DRIPSTONE;
    }
}
