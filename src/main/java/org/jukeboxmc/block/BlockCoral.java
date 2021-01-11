package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoral extends Block {

    public BlockCoral() {
        super( "minecraft:coral" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getCoralColor().ordinal() );
    }

    public void setCoralColor( CoralColor coralColor ) {
        this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ).toUpperCase() ) : CoralColor.BLUE;
    }

    public void setDead( boolean value ) {
        this.setState( "dead_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDead() {
        return this.stateExists( "dead_bit" ) && this.getByteState( "dead_bit" ) == 1;
    }

    public enum CoralColor {
        BLUE,
        PINK,
        PURPLE,
        RED,
        YELLOW
    }
}
