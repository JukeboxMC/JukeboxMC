package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLeaves extends Block {

    public BlockLeaves() {
        super( "minecraft:leaves" );
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
        this.setState( "old_leaf_type", leafType.name().toLowerCase() );
    }

    public LeafType getLeafType() {
        return this.stateExists( "old_leaf_type" ) ? LeafType.valueOf( this.getStringState( "old_leaf_type" ).toUpperCase() ) : LeafType.OAK;
    }

    public enum LeafType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }
}
