package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStainedGlass;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedGlass extends Item {

    public ItemStainedGlass( int blockRuntimeId ) {
        super( "minecraft:stained_glass", blockRuntimeId );
    }

    @Override
    public ItemType getItemType() {
        switch ( this.getColor() ) {
            case SILVER:
                return ItemType.SILVER_STAINED_GLASS;
            case GRAY:
                return ItemType.GRAY_STAINED_GLASS;
            case BLACK:
                return ItemType.BLACK_STAINED_GLASS;
            case BROWN:
                return ItemType.BROWN_STAINED_GLASS;
            case RED:
                return ItemType.RED_STAINED_GLASS;
            case ORANGE:
                return ItemType.ORANGE_STAINED_GLASS;
            case YELLOW:
                return ItemType.YELLOW_STAINED_GLASS;
            case LIME:
                return ItemType.LIME_STAINED_GLASS;
            case GREEN:
                return ItemType.GREEN_STAINED_GLASS;
            case CYAN:
                return ItemType.CYAN_STAINED_GLASS;
            case LIGHT_BLUE:
                return ItemType.LIGHT_BLUE_STAINED_GLASS;
            case BLUE:
                return ItemType.BLUE_STAINED_GLASS;
            case PURPLE:
                return ItemType.PURPLE_STAINED_GLASS;
            case MAGENTA:
                return ItemType.MAGENTA_STAINED_GLASS;
            case PINK:
                return ItemType.PINK_STAINED_GLASS;
            default:
                return ItemType.WHITE_STAINED_GLASS_PANE;
        }
    }

    @Override
    public BlockStainedGlass getBlock() {
        return (BlockStainedGlass) BlockType.getBlock( this.blockRuntimeId );
    }


    public BlockColor getColor() {
        return this.getBlock().getColor();
    }
}
