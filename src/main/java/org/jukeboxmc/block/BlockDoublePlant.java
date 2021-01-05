package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoublePlant extends Block {

    public BlockDoublePlant() {
        super( "minecraft:double_plant" );
    }

    public BlockDoublePlant setPlantType( PlantType plantType ) {
        return this.setState( "double_plant_type", plantType.name().toLowerCase() );
    }

    public PlantType getPlantType() {
        return this.stateExists( "double_plant_type" ) ? PlantType.valueOf( this.getStringState( "double_plant_type" ).toUpperCase() ) : PlantType.SUNFLOWER;
    }

    public void setUpperBlock( boolean value ) {
        this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock( boolean value ) {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
    }

    public enum PlantType {
        FERN,
        GRASS,
        PAEONIA,
        ROSE,
        SUNFLOWER,
        SYRINGA
    }
}
