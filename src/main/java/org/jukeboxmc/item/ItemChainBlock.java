package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChain;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChainBlock extends Item {

    public ItemChainBlock() {
        super( "minecraft:item.chain", -286 );
    }

    @Override
    public BlockChain getBlock() {
        return new BlockChain();
    }
}
