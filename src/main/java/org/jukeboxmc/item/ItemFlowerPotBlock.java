package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFlowerPot;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFlowerPotBlock extends Item {

    public ItemFlowerPotBlock() {
        super( "minecraft:item.flower_pot", 140 );
    }

    @Override
    public BlockFlowerPot getBlock() {
        return new BlockFlowerPot();
    }
}
