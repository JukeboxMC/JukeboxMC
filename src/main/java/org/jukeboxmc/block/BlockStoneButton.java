package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneButton extends Block {

    public BlockStoneButton() {
        super( "minecraft:stone_button" );
    }

    public void setButtonPressed( boolean value ) {
        this.setState( "button_pressed_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isButtonPressed() {
        return this.stateExists( "button_pressed_bit" ) && this.getByteState( "button_pressed_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}