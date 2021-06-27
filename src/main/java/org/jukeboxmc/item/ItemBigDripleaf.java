package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBigDripleaf;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBigDripleaf extends Item{

    public ItemBigDripleaf() {
        super( "minecraft:big_dripleaf" );
    }

    @Override
    public BlockBigDripleaf getBlock() {
        return new BlockBigDripleaf();
    }
}
