package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWood extends Block {

    public BlockWood() {
        super( "minecraft:wood" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getWoodType().ordinal() );
    }

    public void setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ).toUpperCase() ) : WoodType.OAK;
    }

    public void setStripped( boolean value ) {
        this.setState( "stripped_bit", value ? (byte) 1: (byte) 0 );
    }

    public boolean isStripped() {
        return this.stateExists( "stripped_bit" ) && this.getByteState( "stripped_bit" ) == 1;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }

    public enum WoodType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }

    public enum Axis {
        Y,
        X,
        Z
    }
}
