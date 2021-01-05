package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleWoodenSlab extends Block {

    public BlockDoubleWoodenSlab() {
        super( "minecraft:double_wooden_slab" );
    }

    public void setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }

    public void setWoodType( BlockPlanks.WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
    }

    public BlockPlanks.WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? BlockPlanks.WoodType.valueOf( this.getStringState( "wood_type" ).toUpperCase() ) : BlockPlanks.WoodType.OAK;
    }

    public enum WoodType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }
}
