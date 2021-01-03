package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockConcretePowder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemConcretePowder extends Item {

    public ItemConcretePowder() {
        super("minecraft:concrete_powder", 237);
    }

    @Override
    public Block getBlock() {
        return new BlockConcretePowder();
    }
}
