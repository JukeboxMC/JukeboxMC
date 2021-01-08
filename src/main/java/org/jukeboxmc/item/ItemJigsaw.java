package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJigsaw;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJigsaw extends Item {

    public ItemJigsaw() {
        super( "minecraft:jigsaw", -211 );
    }

    @Override
    public BlockJigsaw getBlock() {
        return new BlockJigsaw();
    }
}
