package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStickyPiston;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStickyPiston extends Item {

    public ItemStickyPiston() {
        super( "minecraft:sticky_piston", 29 );
    }

    @Override
    public BlockStickyPiston getBlock() {
        return new BlockStickyPiston();
    }
}
