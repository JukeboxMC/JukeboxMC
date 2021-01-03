package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCartographyTableBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCartographyTable extends Item {

    public ItemCartographyTable() {
        super( "minecraft:cartography_table", -200 );
    }

    @Override
    public Block getBlock() {
        return new BlockCartographyTableBlock();
    }
}
