package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGlowstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlowstone extends Block {

    public BlockGlowstone() {
        super( "minecraft:glowstone" );
    }

    @Override
    public ItemGlowstone toItem() {
        return new ItemGlowstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLOWSTONE;
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

}
