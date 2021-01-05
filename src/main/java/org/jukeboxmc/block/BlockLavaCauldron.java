package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLavaCauldron extends Block {

    public BlockLavaCauldron() {
        super( "minecraft:lava_cauldron" );
    }

    public void setFillLevel( int value ) { //0-6
        this.setState( "fill_level", value );
    }

    public int getFillLevel() {
        return this.stateExists( "fill_level" ) ? this.getIntState( "fill_level" ) : 0;
    }

    public void setLiquid( LiquidType liquidType ) {
        this.setState( "cauldron_liquid", liquidType.name().toLowerCase() );
    }

    public LiquidType getLiquidType() {
        return this.stateExists( "cauldron_liquid" ) ? LiquidType.valueOf( this.getStringState( "cauldron_liquid" ).toUpperCase() ) : LiquidType.WATER;
    }

    public enum LiquidType {
        WATER,
        LAVA
    }
}
