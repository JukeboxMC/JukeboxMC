package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockStainedGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedGlassPane extends Item {

    public ItemStainedGlassPane() {
        super(160, 5823);
    }

    @Override
    public ItemType getItemType() {
        switch (this.getColor()) {
            case SILVER:
                return ItemType.SILVER_STAINED_GLASS_PANE;
            case GRAY:
                return ItemType.GRAY_STAINED_GLASS_PANE;
            case BLACK:
                return ItemType.BLACK_STAINED_GLASS_PANE;
            case BROWN:
                return ItemType.BROWN_STAINED_GLASS_PANE;
            case RED:
                return ItemType.RED_STAINED_GLASS_PANE;
            case ORANGE:
                return ItemType.ORANGE_STAINED_GLASS_PANE;
            case YELLOW:
                return ItemType.YELLOW_STAINED_GLASS_PANE;
            case LIME:
                return ItemType.LIME_STAINED_GLASS_PANE;
            case GREEN:
                return ItemType.GREEN_STAINED_GLASS_PANE;
            case CYAN:
                return ItemType.CYAN_STAINED_GLASS_PANE;
            case LIGHT_BLUE:
                return ItemType.LIGHT_BLUE_STAINED_GLASS_PANE;
            case BLUE:
                return ItemType.BLUE_STAINED_GLASS_PANE;
            case PURPLE:
                return ItemType.PURPLE_STAINED_GLASS_PANE;
            case MAGENTA:
                return ItemType.MAGENTA_STAINED_GLASS_PANE;
            case PINK:
                return ItemType.PINK_STAINED_GLASS_PANE;
            default:
                return ItemType.WHITE_STAINED_GLASS_PANE;
        }
    }

    @Override
    public BlockStainedGlassPane getBlock() {
        return new BlockStainedGlassPane().setColor(this.getColor());
    }

    public ItemStainedGlassPane setColor(BlockColor color) {
        switch (color) {
            case SILVER:
                this.setBlockRuntimeId(5831);
                break;
            case GRAY:
                this.setBlockRuntimeId(5830);
                break;
            case BLACK:
                this.setBlockRuntimeId(5838);
                break;
            case BROWN:
                this.setBlockRuntimeId(5835);
                break;
            case RED:
                this.setBlockRuntimeId(5837);
                break;
            case ORANGE:
                this.setBlockRuntimeId(5824);
                break;
            case YELLOW:
                this.setBlockRuntimeId(5827);
                break;
            case LIME:
                this.setBlockRuntimeId(5828);
                break;
            case GREEN:
                this.setBlockRuntimeId(5836);
                break;
            case CYAN:
                this.setBlockRuntimeId(5832);
                break;
            case LIGHT_BLUE:
                this.setBlockRuntimeId(5826);
                break;
            case BLUE:
                this.setBlockRuntimeId(5834);
                break;
            case PURPLE:
                this.setBlockRuntimeId(5833);
                break;
            case MAGENTA:
                this.setBlockRuntimeId(5825);
                break;
            case PINK:
                this.setBlockRuntimeId(5829);
                break;
            default:
                this.setBlockRuntimeId(5823);
                break;
        }

        return this;
    }

    public BlockColor getColor() {
        switch (this.blockRuntimeId) {
            case 5831:
                return BlockColor.SILVER;
            case 5830:
                return BlockColor.GRAY;
            case 5838:
                return BlockColor.BLACK;
            case 5835:
                return BlockColor.BROWN;
            case 5837:
                return BlockColor.RED;
            case 5824:
                return BlockColor.ORANGE;
            case 5827:
                return BlockColor.YELLOW;
            case 5828:
                return BlockColor.LIME;
            case 5836:
                return BlockColor.GREEN;
            case 5832:
                return BlockColor.CYAN;
            case 5826:
                return BlockColor.LIGHT_BLUE;
            case 5834:
                return BlockColor.BLUE;
            case 5833:
                return BlockColor.PURPLE;
            case 5825:
                return BlockColor.MAGENTA;
            case 5829:
                return BlockColor.PINK;
            default:
                return BlockColor.WHITE;
        }
    }
}
