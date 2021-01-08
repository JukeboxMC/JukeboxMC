package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFrame;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFrameBlock extends Item {

    public ItemFrameBlock() {
        super( "minecraft:item.frame", 199 );
    }

    @Override
    public BlockFrame getBlock() {
        return new BlockFrame();
    }
}
