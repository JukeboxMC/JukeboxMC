package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFlowerPot;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFlowerPot extends Item {

    public ItemFlowerPot() {
        super( "minecraft:flower_pot", 504 );
    }

    @Override
    public BlockFlowerPot getBlock() {
        return new BlockFlowerPot();
    }
}
