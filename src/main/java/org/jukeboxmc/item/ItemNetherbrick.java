package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherBrickBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherbrick extends Item {

    public ItemNetherbrick() {
        super ( "minecraft:nether_brick" );
    }

    @Override
    public BlockNetherBrickBlock getBlock() {
        return new BlockNetherBrickBlock();
    }
}
