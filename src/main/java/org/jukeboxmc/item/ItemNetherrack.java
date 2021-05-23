package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockNetherrack;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherrack extends Item {

    public ItemNetherrack() {
        super( 87 );
    }

    @Override
    public BlockNetherrack getBlock() {
        return new BlockNetherrack();
    }
}
