package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab extends Block {

    public BlockStoneSlab() {
        super( "minecraft:stone_slab" );
    }

    public void setStoneSlabType( StoneSlabType stoneSlabType ) {
        this.setState( "stone_slab_type", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type" ).toUpperCase() ) : StoneSlabType.SMOOTH_STONE;
    }

    public void setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }

    public enum StoneSlabType {
        SMOOTH_STONE,
        SANDSTONE,
        WOOD,
        COBBLESTONE,
        BRICK,
        STONE_BRICK,
        QUARTZ,
        NETHER_BRICK
    }
}
