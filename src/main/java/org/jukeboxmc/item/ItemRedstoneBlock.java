package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstoneBlock extends Item {

    public ItemRedstoneBlock() {
        super( "minecraft:redstone_block", 152 );
    }

    @Override
    public BlockRedstoneBlock getBlock() {
        return new BlockRedstoneBlock();
    }
}
