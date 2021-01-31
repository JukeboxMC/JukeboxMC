package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLeaves2 extends Block {

    public BlockLeaves2() {
        super( "minecraft:leaves2" );
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

    public void setLeafType( LeafType leafType ) {
        this.setState( "new_leaf_type", leafType.name().toLowerCase() );
    }

    public LeafType getLeafType() {
        return this.stateExists( "new_leaf_type" ) ? LeafType.valueOf( this.getStringState( "new_leaf_type" ).toUpperCase() ) : LeafType.ACACIA;
    }

    public enum LeafType {
        ACACIA,
        DARK_OAK
    }
}
