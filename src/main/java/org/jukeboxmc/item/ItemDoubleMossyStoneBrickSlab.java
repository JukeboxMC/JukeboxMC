package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDoubleMossyStoneBrickSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleMossyStoneBrickSlab extends Item {

    public ItemDoubleMossyStoneBrickSlab() {
        super( "minecraft:double_stone_slab4", -166 );
    }

    @Override
    public Block getBlock() {
        return new BlockDoubleMossyStoneBrickSlab();
    }
}
