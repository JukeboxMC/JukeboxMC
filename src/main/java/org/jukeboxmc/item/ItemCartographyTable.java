package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCartographyTableBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCartographyTable extends Item {

    public ItemCartographyTable() {
        super( -200 );
    }

    @Override
    public BlockCartographyTableBlock getBlock() {
        return new BlockCartographyTableBlock();
    }
}
