package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.TorchFacing;
import org.jukeboxmc.item.ItemColoredTorchBP;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockColoredTorchBP extends Block {

    public BlockColoredTorchBP() {
        super( "minecraft:colored_torch_bp" );
    }

    @Override
    public ItemColoredTorchBP toItem() {
        return new ItemColoredTorchBP();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COLORED_TORCH_BP;
    }

    public void setTorchFacing( TorchFacing torchFacing ) {
        this.setState( "torch_facing_direction", torchFacing.name().toLowerCase() );
    }

    public TorchFacing getTorchFacing() {
        return this.stateExists( "torch_facing_direction" ) ? TorchFacing.valueOf( this.getStringState( "torch_facing_direction" ) ) : TorchFacing.TOP;
    }

    public void setColor( boolean value ) {
        this.setState( "color_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isColor() {
        return this.stateExists( "color_bit" ) && this.getByteState( "color_bit" ) == 1;
    }
}
