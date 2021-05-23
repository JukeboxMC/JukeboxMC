package org.jukeboxmc.block;

import org.jukeboxmc.block.type.CoralColor;
import org.jukeboxmc.item.ItemCoral;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoral extends Block {

    public BlockCoral() {
        super( "minecraft:coral" );
    }

    @Override
    public ItemCoral toItem() {
        return new ItemCoral( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL;
    }

    public BlockCoral setCoralColor( CoralColor coralColor ) {
       return this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ) ) : CoralColor.BLUE;
    }

    public BlockCoral setDead( boolean value ) {
       return this.setState( "dead_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDead() {
        return this.stateExists( "dead_bit" ) && this.getByteState( "dead_bit" ) == 1;
    }
}
