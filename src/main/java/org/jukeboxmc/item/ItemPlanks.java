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

    public void setWoodenType( WoodenType woodenType ) {
        this.setMeta( woodenType.ordinal() );
    }

    public WoodenType getWoodenType() {
        return WoodenType.values()[this.getMeta()];
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
