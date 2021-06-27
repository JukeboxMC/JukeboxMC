package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemAmethystCluster;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAmethystCluster extends Block {

    public BlockAmethystCluster() {
        super( "minecraft:amethyst_cluster" );
    }

    @Override
    public ItemAmethystCluster toItem() {
        return new ItemAmethystCluster();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AMETHYST_CLUSTER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
