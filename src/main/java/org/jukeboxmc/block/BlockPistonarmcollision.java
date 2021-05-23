package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemPistonarmcollision;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPistonarmcollision extends Block {

    public BlockPistonarmcollision() {
        super( "minecraft:pistonarmcollision" );
    }

    @Override
    public ItemPistonarmcollision toItem() {
        return new ItemPistonarmcollision();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PISTONARMCOLLISION;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
