package org.jukeboxmc.block;

import org.jukeboxmc.block.type.GrassType;
import org.jukeboxmc.item.ItemTallgrass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTallGrass extends Block {

    public BlockTallGrass() {
        super( "minecraft:tallgrass" );
    }

    @Override
    public ItemTallgrass toItem() {
        return new ItemTallgrass( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TALLGRASS;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public BlockTallGrass setGrassType( GrassType grassType ) {
        return this.setState( "tall_grass_type", grassType.name().toLowerCase() );
    }

    public GrassType getGrassType() {
        return this.stateExists( "tall_grass_type" ) ? GrassType.valueOf( this.getStringState( "tall_grass_type" ) ) : GrassType.DEFAULT;
    }

}
