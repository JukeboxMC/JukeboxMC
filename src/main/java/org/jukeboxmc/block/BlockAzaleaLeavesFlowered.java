package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAzaleaLeavesFlowered;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAzaleaLeavesFlowered extends Block{

    public BlockAzaleaLeavesFlowered() {
        super( "minecraft:azalea_leaves_flowered" );
    }

    @Override
    public ItemAzaleaLeavesFlowered toItem() {
        return new ItemAzaleaLeavesFlowered();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AZALEA_LEAVES_FLOWERED;
    }

    public void setPersistent( boolean value ) {
        this.setState( "persistent_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isPersistent() {
        return this.stateExists( "persistent_bit" ) && this.getByteState( "persistent_bit" ) == 1;
    }

    public void setUpdate( boolean value ) {
        this.setState( "update_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpdate() {
        return this.stateExists( "update_bit" ) && this.getByteState( "update_bit" ) == 1;
    }
}
