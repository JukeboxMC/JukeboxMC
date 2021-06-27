package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFloweringAzalea;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFloweringAzalea extends Item{

    public ItemFloweringAzalea() {
        super( "minecraft:flowering_azalea" );
    }

    @Override
    public BlockFloweringAzalea getBlock() {
        return new BlockFloweringAzalea();
    }
}
