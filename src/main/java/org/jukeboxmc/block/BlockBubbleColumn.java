package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBubbleColumn;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBubbleColumn extends Block {

    public BlockBubbleColumn() {
        super( "minecraft:bubble_column" );
    }

    @Override
    public ItemBubbleColumn toItem() {
        return new ItemBubbleColumn();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BUBBLE_COLUMN;
    }

    public void setDragDown( boolean value ) {
        this.setState( "drag_down", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDragDown() {
        return this.stateExists( "drag_down" ) && this.getByteState( "drag_down" ) == 1;
    }
}
