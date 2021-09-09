package org.jukeboxmc.block;

import org.jukeboxmc.block.type.CoralColor;
import org.jukeboxmc.item.ItemCoralBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoralBlock extends Block {

    public BlockCoralBlock() {
        super( "minecraft:coral_block" );
    }

    @Override
    public ItemCoralBlock toItem() {
        return new ItemCoralBlock( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL_BLOCK;
    }

    public BlockCoralBlock setCoralColor( CoralColor coralColor ) {
        return this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ) ) : CoralColor.BLUE;
    }

    public BlockCoralBlock setDead( boolean value ) {
       return this.setState( "dead_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDead() {
        return this.stateExists( "dead_bit" ) && this.getByteState( "dead_bit" ) == 1;
    }
}
