package org.jukeboxmc.block;

import org.jukeboxmc.block.type.SaplingType;
import org.jukeboxmc.item.ItemBambooSapling;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBambooSapling extends Block {

    public BlockBambooSapling() {
        super( "minecraft:bamboo_sapling" );
    }

    @Override
    public ItemBambooSapling toItem() {
        return new ItemBambooSapling();
    }

    @Override
    public BlockType getType() {
        return BlockType.BAMBOO_SAPLING;
    }

    public void setSaplingType( SaplingType saplingType ) {
        this.setState( "sapling_type", saplingType.name().toLowerCase() );
    }

    public SaplingType getSaplingType() {
        return this.stateExists( "sapling_type" ) ? SaplingType.valueOf( this.getStringState( "sapling_type" ) ) : SaplingType.OAK;
    }

    public void setAge( boolean value ) {
        this.setState( "age_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean hasAge() {
        return this.stateExists( "age_bit" ) && this.getByteState( "age_bit" ) == 1;
    }

}
