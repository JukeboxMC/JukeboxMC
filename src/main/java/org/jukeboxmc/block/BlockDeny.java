package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeny;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeny extends Block {

    public BlockDeny() {
        super( "minecraft:deny" );
    }

    @Override
    public ItemDeny toItem() {
        return new ItemDeny();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DENY;
    }

}
