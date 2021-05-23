package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEndGateway;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndGateway extends Item {

    public ItemEndGateway() {
        super(  209 );
    }

    @Override
    public BlockEndGateway getBlock() {
        return new BlockEndGateway();
    }
}
