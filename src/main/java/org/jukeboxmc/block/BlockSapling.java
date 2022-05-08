package org.jukeboxmc.block;

import org.jukeboxmc.block.type.SaplingType;
import org.jukeboxmc.item.ItemSapling;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSapling extends Block {

    public BlockSapling() {
        super( "minecraft:sapling" );
    }

    @Override
    public ItemSapling toItem() {
        return new ItemSapling(this.runtimeId );
    }

    @Override
    public BlockType getType() {
        return BlockType.SAPLING;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public BlockSapling setSaplingType( SaplingType saplingType ) {
       return this.setState( "sapling_type", saplingType.name().toLowerCase() );
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
