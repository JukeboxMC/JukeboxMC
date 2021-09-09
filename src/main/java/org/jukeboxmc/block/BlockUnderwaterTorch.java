package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.TorchFacing;
import org.jukeboxmc.item.ItemUnderwaterTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockUnderwaterTorch extends Block {

    public BlockUnderwaterTorch() {
        super( "minecraft:underwater_torch" );
    }

    @Override
    public ItemUnderwaterTorch toItem() {
        return new ItemUnderwaterTorch();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.UNDERWATER_TORCH;
    }

    public void setTorchFacing( TorchFacing torchFacing ) {
        this.setState( "torch_facing_direction", torchFacing.name().toLowerCase() );
    }

    public TorchFacing getTorchFacing() {
        return this.stateExists( "torch_facing_direction" ) ? TorchFacing.valueOf( this.getStringState( "torch_facing_direction" ) ) : TorchFacing.TOP;
    }
}
