package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaLeaves;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaLeaves extends Item {

    public ItemAcaciaLeaves() {
        super( "minecraft:leaves2", 161 );
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaLeaves();
    }
}
