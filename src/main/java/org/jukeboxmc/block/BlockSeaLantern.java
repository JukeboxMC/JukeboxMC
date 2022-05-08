package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSealantern;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSeaLantern extends Block {

    public BlockSeaLantern() {
        super( "minecraft:sea_lantern" );
    }

    @Override
    public ItemSealantern toItem() {
        return new ItemSealantern();
    }

    @Override
    public BlockType getType() {
        return BlockType.SEALANTERN;
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

}
