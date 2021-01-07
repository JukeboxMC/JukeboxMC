package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockTallGrass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTallgrass extends Item {

    public ItemTallgrass() {
        super( "minecraft:tallgrass", 31 );
    }

    @Override
    public Block getBlock() {
        return new BlockTallGrass();
    }

    public void setGrassType( GrassType grassType ) {
        if ( grassType == GrassType.GRASS ) {
            this.setMeta( 1 );
        } else {
            this.setMeta( 2 );
        }
    }

    public GrassType getGrassType() {
        if ( this.getMeta() == 1 ) {
            return GrassType.GRASS;
        } else {
            return GrassType.FERN;
        }
    }

    public enum GrassType {
        GRASS,
        FERN
    }
}
