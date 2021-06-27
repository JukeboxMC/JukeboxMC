package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAmethystBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAmethystBlock extends Item{

    public ItemAmethystBlock() {
        super( "minecraft:amethyst_block" );
    }

    @Override
    public BlockAmethystBlock getBlock() {
        return new BlockAmethystBlock();
    }
}
