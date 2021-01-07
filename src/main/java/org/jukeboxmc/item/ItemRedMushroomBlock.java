package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedMushroomBlock extends Item {

    public ItemRedMushroomBlock() {
        super( "minecraft:red_mushroom_block", 100 );
    }

    public void setMushroomType( MushroomType mushroomType ) {
        if ( mushroomType == MushroomType.MUSHROOM_BROWN ) {
            this.setMeta( 0 );
        } else {
            this.setMeta( 14 );
        }
    }

    public MushroomType getMushroomType() {
        if ( this.getMeta() == 0 ) {
            return MushroomType.MUSHROOM_BROWN;
        } else {
            return MushroomType.MUSHROOM_RED;
        }
    }

    public enum MushroomType {
        MUSHROOM_BROWN,
        MUSHROOM_RED
    }

}
