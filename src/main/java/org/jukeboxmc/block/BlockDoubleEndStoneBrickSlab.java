package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleEndStoneBrickSlab extends Block {

    public BlockDoubleEndStoneBrickSlab() {
        super( "minecraft:double_stone_slab3" );
    }

    public void setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }

    public void setStoneSlabType( StoneSlabType stoneSlabType ) {
        this.setState( "stone_slab_type_4", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type_4" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type_4" ).toUpperCase() ) : StoneSlabType.STONE;
    }

    public enum StoneSlabType {
        MOSSY_STONE_BRICK,
        SMOOTH_QUARTZ,
        STONE,
        CUT_SANDSTONE,
        CUT_RED_SANDSTONE
    }

}
