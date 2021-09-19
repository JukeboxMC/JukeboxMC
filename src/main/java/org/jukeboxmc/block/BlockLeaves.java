package org.jukeboxmc.block;

import org.jukeboxmc.block.type.LeafType;
import org.jukeboxmc.item.ItemLeaves;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLeaves extends BlockWaterlogable {

    public BlockLeaves() {
        super( "minecraft:leaves" );
    }

    @Override
    public ItemLeaves toItem() {
        return new ItemLeaves( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LEAVES;
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHEARS;
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

    public BlockLeaves setLeafType( LeafType leafType ) {
        return this.setState( "old_leaf_type", leafType.name().toLowerCase() );
    }

    public LeafType getLeafType() {
        return this.stateExists( "old_leaf_type" ) ? LeafType.valueOf( this.getStringState( "old_leaf_type" ) ) : LeafType.OAK;
    }

}
