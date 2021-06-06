package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAir;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAir extends BlockWaterlogable {

    public BlockAir() {
        super( "minecraft:air" );
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public ItemAir toItem() {
        return new ItemAir();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AIR;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeFlowedInto() {
        return true;
    }
}
