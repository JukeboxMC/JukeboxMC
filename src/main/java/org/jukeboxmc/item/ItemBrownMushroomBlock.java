package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBrownMushroomBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrownMushroomBlock extends Item {

    public ItemBrownMushroomBlock() {
        super( "minecraft:brown_mushroom_block", 99 );
    }

    @Override
    public BlockBrownMushroomBlock getBlock() {
        return new BlockBrownMushroomBlock();
    }

    public void setMushroomType( MushroomType mushroomType ) {
        switch ( mushroomType ) {
            case BROWN_MUSHROOM:
                this.setMeta( 14 );
                break;
            case MUSHROOM_STEM:
                this.setMeta( 15 );
                break;
            default:
                this.setMeta( 0 );
                break;
        }
    }

    public MushroomType getMushroomType() {
        switch ( this.getMeta() ) {
            case 14:
                return MushroomType.BROWN_MUSHROOM;
            case 15:
                return MushroomType.MUSHROOM_STEM;
            default:
                return MushroomType.MUSHROOM;
        }
    }

    public enum MushroomType {
        BROWN_MUSHROOM,
        MUSHROOM_STEM,
        MUSHROOM,
    }

}
