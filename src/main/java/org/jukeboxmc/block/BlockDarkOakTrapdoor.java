package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkOakTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakTrapdoor extends BlockTrapdoor {

    public BlockDarkOakTrapdoor() {
        super( "minecraft:dark_oak_trapdoor" );
    }

    @Override
    public ItemDarkOakTrapdoor toItem() {
        return new ItemDarkOakTrapdoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.DARK_OAK_TRAPDOOR;
    }

}
