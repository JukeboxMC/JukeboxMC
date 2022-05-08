package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemJigsaw;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJigsaw extends Block {

    public BlockJigsaw() {
        super( "minecraft:jigsaw" );
    }

    @Override
    public ItemJigsaw toItem() {
        return new ItemJigsaw();
    }

    @Override
    public BlockType getType() {
        return BlockType.JIGSAW;
    }

    public void setRotation( int value ) { //0-3
        this.setState( "rotation", value );
    }

    public int getRotation() {
        return this.stateExists( "rotation" ) ? this.getIntState( "rotation" ) : 0;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
