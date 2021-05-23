package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBamboo;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBamboo extends Block {

    public BlockBamboo() {
        super( "minecraft:bamboo" );
    }

    @Override
    public ItemBamboo toItem() {
        return new ItemBamboo();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BAMBOO;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setBambooLeafSize( BambooLeafSize bambooLeafSize ) {
        this.setState( "bamboo_leaf_size", bambooLeafSize.name().toLowerCase() );
    }

    public BambooLeafSize getBambooLeafSize() {
        return this.stateExists( "bamboo_leaf_size" ) ? BambooLeafSize.valueOf( this.getStringState( "bamboo_leaf_size" ) ) : BambooLeafSize.NO_LEAVES;
    }

    public void setAge( boolean value ) {
        this.setState( "age_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean hasAge() {
        return this.stateExists( "age_bit" ) && this.getByteState( "age_bit" ) == 1;
    }

    public void setBambooStalkThickness( BambooStalkThickness bambooStalkThickness ) {
        this.setState( "bamboo_stalk_thickness", bambooStalkThickness.name().toLowerCase() );
    }

    public BambooStalkThickness getBambooStalkThickness() {
        return this.stateExists( "bamboo_stalk_thickness" ) ? BambooStalkThickness.valueOf( this.getStringState( "bamboo_stalk_thickness" ) ) : BambooStalkThickness.THIN;
    }

    public enum BambooLeafSize {
        NO_LEAVES,
        SMALL_LEAVES,
        LARGE_LEAVES
    }

    public enum BambooStalkThickness {
        THIN,
        THICK
    }
}
