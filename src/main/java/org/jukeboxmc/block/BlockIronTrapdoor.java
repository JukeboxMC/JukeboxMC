package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIronTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronTrapdoor extends BlockTrapdoor {

    public BlockIronTrapdoor() {
        super( "minecraft:iron_trapdoor" );
    }

    @Override
    public ItemIronTrapdoor toItem() {
        return new ItemIronTrapdoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.IRON_TRAPDOOR;
    }

}
