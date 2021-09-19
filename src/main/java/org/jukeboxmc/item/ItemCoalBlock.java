package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoal;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoalBlock extends Item {

    public ItemCoalBlock() {
        super ( "minecraft:coal_block" );
    }

    @Override
    public BlockCoal getBlock() {
        return new BlockCoal();
    }


}
