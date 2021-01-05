package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedFlower extends Block {

    public BlockRedFlower() {
        super( "minecraft:red_flower" );
    }

    public void setFlowerType( FlowerType flowerType ) {
        this.setState( "flower_type", flowerType.name().toLowerCase() );
    }

    public FlowerType getFlowerType() {
        return this.stateExists( "flower_type" ) ? FlowerType.valueOf( this.getStringState( "flower_type" ).toUpperCase() ) : FlowerType.TULIP_RED;
    }

    public enum FlowerType {
        POPPY,
        ORCHID,
        ALLIUM,
        HOUSTONIA,
        TULIP_RED,
        TULIP_ORANGE,
        TULIP_WHITE,
        TULIP_PINK,
        OXEYE,
        CORNFLOWER,
        LILY_OF_THE_VALLEY
    }
}
