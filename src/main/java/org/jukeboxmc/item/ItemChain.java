package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChain;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChain extends Item {

    public ItemChain() {
        super( "minecraft:chain", 607 );
    }

    @Override
    public BlockChain getBlock() {
        return new BlockChain();
    }
}
