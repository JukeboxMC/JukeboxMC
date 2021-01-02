package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockTallGrass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTallgrass extends Item {

    public ItemTallgrass() {
        super( "minecraft:tallgrass", 31 );
    }

    @Override
    public Block getBlock() {
        return new BlockTallGrass();
    }
}
