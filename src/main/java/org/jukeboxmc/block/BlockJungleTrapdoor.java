package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemJungleTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleTrapdoor extends BlockTrapdoor {

    public BlockJungleTrapdoor() {
        super( "minecraft:jungle_trapdoor" );
    }

    @Override
    public ItemJungleTrapdoor toItem() {
        return new ItemJungleTrapdoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.JUNGLE_TRAPDOOR;
    }

}
