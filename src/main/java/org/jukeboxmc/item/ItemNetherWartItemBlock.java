package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherWartBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherWartItemBlock extends Item {

    public ItemNetherWartItemBlock() {
        super ( "minecraft:item.nether_wart" );
    }

    @Override
    public BlockNetherWartBlock getBlock() {
        return new BlockNetherWartBlock();
    }
}
