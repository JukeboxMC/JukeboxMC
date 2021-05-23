package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEmeraldBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEmeraldBlock extends Item {

    public ItemEmeraldBlock() {
        super( 133 );
    }

    @Override
    public BlockEmeraldBlock getBlock() {
        return new BlockEmeraldBlock();
    }
}
