package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSapling extends Block {

    public BlockSapling() {
        super( "minecraft:sapling" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSaplingType().ordinal() );
    }

    public void setSaplingType( SaplingType saplingType ) {
        this.setState( "sapling_type", saplingType.name().toLowerCase() );
    }

    public SaplingType getSaplingType() {
        return this.stateExists( "sapling_type" ) ? SaplingType.valueOf( this.getStringState( "sapling_type" ).toUpperCase() ) : SaplingType.OAK;
    }

    public void setAge( boolean value ) {
        this.setState( "age_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean hasAge() {
        return this.stateExists( "age_bit" ) && this.getByteState( "age_bit" ) == 1;
    }

    public enum SaplingType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }
}
