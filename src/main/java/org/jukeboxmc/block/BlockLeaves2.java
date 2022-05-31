package org.jukeboxmc.block;

import org.jukeboxmc.block.type.LeafType2;
import org.jukeboxmc.item.ItemLeaves2;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLeaves2 extends BlockWaterlogable {

    public BlockLeaves2() {
        super( "minecraft:leaves2" );
    }

    @Override
    public ItemLeaves2 toItem() {
        return new ItemLeaves2( this.runtimeId );
    }

    @Override
    public BlockType getType() {
        return switch ( this.getLeafType() ) {
            case ACACIA -> BlockType.ACACIA_LEAVES;
            case DARK_OAK -> BlockType.DARK_OAK_LEAVES;
        };
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

    public BlockLeaves2 setLeafType( LeafType2 leafType ) {
        return this.setState( "new_leaf_type", leafType.name().toLowerCase() );
    }

    public LeafType2 getLeafType() {
        return this.stateExists( "new_leaf_type" ) ? LeafType2.valueOf( this.getStringState( "new_leaf_type" ) ) : LeafType2.ACACIA;
    }
}
