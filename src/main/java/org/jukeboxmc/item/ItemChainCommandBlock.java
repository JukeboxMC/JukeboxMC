package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChainCommandBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChainCommandBlock extends Item {

    public ItemChainCommandBlock() {
        super( "minecraft:chain_command_block", 189 );
    }

    @Override
    public BlockChainCommandBlock getBlock() {
        return new BlockChainCommandBlock();
    }
}
