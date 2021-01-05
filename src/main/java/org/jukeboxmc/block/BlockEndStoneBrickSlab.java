package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndStoneBrickSlab extends Block {

    public BlockEndStoneBrickSlab() {
        super( "minecraft:stone_slab3" );
    }

    public void setStoneSlabType( StoneSlabType stoneSlabType ) {
        this.setState( "stone_slab_type_3", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type_3" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type_3" ).toUpperCase() ) : StoneSlabType.END_STONE_BRICK;
    }

    public void setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }

    public enum StoneSlabType {
        END_STONE_BRICK,
        SMOOTH_RED_SANDSTONE,
        POLISHED_ANDESITE,
        ANDESITE,
        DIORITE,
        POLISHED_DIORITE,
        GRANITE,
        POLISHED_GRANITE
    }
}
