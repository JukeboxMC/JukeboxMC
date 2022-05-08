package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.TorchFacing;
import org.jukeboxmc.item.ItemColoredTorchRG;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockColoredTorchRG extends Block {

    public BlockColoredTorchRG() {
        super( "minecraft:colored_torch_rg" );
    }

    @Override
    public ItemColoredTorchRG toItem() {
        return new ItemColoredTorchRG();
    }

    @Override
    public BlockType getType() {
        return BlockType.COLORED_TORCH_RG;
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
