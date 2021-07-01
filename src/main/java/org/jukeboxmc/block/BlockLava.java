package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLava;
import org.jukeboxmc.world.Dimension;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLava extends BlockLiquid {

    public BlockLava() {
        super( "minecraft:lava" );
    }

    @Override
    public ItemLava toItem() {
        return new ItemLava();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LAVA;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public int getTickRate() {
        if ( this.location.getDimension() == Dimension.NETHER ) {
            return 10;
        }
        return 30;
    }

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockLava blockLava = new BlockLava();
        blockLava.setLiquidDepth( liquidDepth );
        return blockLava;
    }
}
