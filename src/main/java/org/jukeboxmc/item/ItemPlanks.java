package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPlanks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPlanks extends Item {

    public ItemPlanks() {
        super( "minecraft:planks", 5 );
    }

    @Override
    public Block getBlock() {
        return new BlockPlanks();
    }

    public WoodenType getWoodenType() {
        return WoodenType.values()[this.meta];
    }

    public void setWoodenType( WoodenType woodenType ) {
        switch ( woodenType ) {
            case SPRUCE:
                this.setMeta( 1 );
                break;
            case BIRCH:
                this.setMeta( 2 );
                break;
            case JUNGLE:
                this.setMeta( 3 );
                break;
            case ACACIA:
                this.setMeta( 4 );
                break;
            case DARK_OAK:
                this.setMeta( 5 );
                break;
            default:
                this.setMeta( 0 );
                break;
        }
    }

    public enum WoodenType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }

}
