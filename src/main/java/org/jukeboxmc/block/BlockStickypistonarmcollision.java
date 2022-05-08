package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemStickypistonarmcollision;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStickypistonarmcollision extends Block {

    public BlockStickypistonarmcollision() {
        super( "minecraft:sticky_piston_arm_collision" );
    }

    @Override
    public ItemStickypistonarmcollision toItem() {
        return new ItemStickypistonarmcollision();
    }

    @Override
    public BlockType getType() {
        return BlockType.STICKYPISTONARMCOLLISION;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }

}
