package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.TorchFacing;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedstoneTorch extends Block {

    public BlockRedstoneTorch() {
        super( "minecraft:redstone_torch" );
    }

    public void setTorchFacing( TorchFacing torchFacing ) {
        this.setState( "torch_facing_direction", torchFacing.name().toLowerCase() );
    }

    public TorchFacing getTorchFacing() {
        return this.stateExists( "torch_facing_direction" ) ? TorchFacing.valueOf( this.getStringState( "torch_facing_direction" ).toUpperCase() ) : TorchFacing.TOP;
    }
}
