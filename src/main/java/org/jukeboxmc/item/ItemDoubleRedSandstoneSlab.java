package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDoubleRedSandstoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleRedSandstoneSlab extends Item {

    public ItemDoubleRedSandstoneSlab() {
        super( "minecraft:double_stone_slab2", 182 );
    }

    @Override
    public Block getBlock() {
        return new BlockDoubleRedSandstoneSlab();
    }
}
