package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCauldron;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCauldronBlock extends Item {

    public ItemCauldronBlock() {
        super( "minecraft:item.cauldron", 118 );
    }

    @Override
    public Block getBlock() {
        return new BlockCauldron();
    }
}
