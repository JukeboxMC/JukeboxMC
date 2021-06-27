package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemFloweringAzalea;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFloweringAzalea extends Block{

    public BlockFloweringAzalea() {
        super( "minecraft:flowering_azalea" );
    }

    @Override
    public ItemFloweringAzalea toItem() {
        return new ItemFloweringAzalea();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FLOWERING_AZALEA;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
