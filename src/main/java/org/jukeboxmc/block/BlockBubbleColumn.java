package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBubbleColumn extends Block {

    public BlockBubbleColumn() {
        super( "minecraft:bubble_column" );
    }

    public void setDragDown( boolean value ) {
        this.setState( "drag_down", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDragDown() {
        return this.stateExists( "drag_down" ) && this.getByteState( "drag_down" ) == 1;
    }
}
