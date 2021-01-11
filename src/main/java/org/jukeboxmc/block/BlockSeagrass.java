package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSeagrass extends Block {

    public BlockSeagrass() {
        super( "minecraft:seagrass" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSeaGrassType().ordinal() );
    }

    public void setSeaGrassType( SeaGrassType seaGrassType ) {
        this.setState( "sea_grass_type",seaGrassType.name().toLowerCase() );
    }

    public SeaGrassType getSeaGrassType() {
        return this.stateExists( "sea_grass_type" ) ? SeaGrassType.valueOf( this.getStringState( "sea_grass_type" ).toUpperCase() ) : SeaGrassType.DEFAULT;
    }

    public enum SeaGrassType {
        DEFAULT,
        DOUBLE_TOP,
        DOUBLE_BOT
    }
}
