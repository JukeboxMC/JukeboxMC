package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedSandstoneSlab extends Block {

    public BlockRedSandstoneSlab() {
        super( "minecraft:stone_slab2" );
    }

    public void setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }

    public void setStoneSlabType( BlockDoubleRedSandstoneSlab.StoneSlabType stoneSlabType ) {
        this.setState( "stone_slab_type_2", stoneSlabType.name().toLowerCase() );
    }

    public BlockDoubleRedSandstoneSlab.StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type_2" ) ? BlockDoubleRedSandstoneSlab.StoneSlabType.valueOf( this.getStringState( "stone_slab_type_2" ).toUpperCase() ) : BlockDoubleRedSandstoneSlab.StoneSlabType.RED_SANDSTONE;
    }

    public enum StoneSlabType {
        RED_SANDSTONE,
        PURPUR,
        PRISMARINE_ROUGH,
        PRISMARINE_DARK,
        PRISMARINE_BRICK,
        MOSSY_COBBLESTONE,
        SMOOTH_SANDSTONE,
        RED_NETHER_BRICK
    }
}
