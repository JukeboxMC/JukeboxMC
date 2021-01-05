package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.TorchFacing;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockColoredTorchBP extends Block {

    public BlockColoredTorchBP() {
        super( "minecraft:colored_torch_bp" );
    }

    public void setTorchFacing( TorchFacing torchFacing ) {
        this.setState( "torch_facing_direction", torchFacing.name().toLowerCase() );
    }

    public TorchFacing getTorchFacing() {
        return this.stateExists( "torch_facing_direction" ) ? TorchFacing.valueOf( this.getStringState( "torch_facing_direction" ).toUpperCase() ) : TorchFacing.TOP;
    }

    public void setColor( boolean value ) {
        this.setState( "color_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isColor() {
        return this.stateExists( "color_bit" ) && this.getByteState( "color_bit" ) == 1;
    }
}
