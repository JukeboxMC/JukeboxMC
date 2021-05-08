package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockStainedGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedGlass extends Item {

    public ItemStainedGlass() {
        super(241, 5807);
    }

    @Override
    public ItemType getItemType() {
        switch (this.getColor()) {
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
        return new BlockStainedGlass().setColor(this.getColor());
    }

    public ItemStainedGlass setColor(BlockColor color) {
        switch (color) {
            case SILVER:
                this.setBlockRuntimeId(5815);
                break;
            case GRAY:
                this.setBlockRuntimeId(5814);
                break;
            case BLACK:
                this.setBlockRuntimeId(5822);
                break;
            case BROWN:
                this.setBlockRuntimeId(5819);
                break;
            case RED:
                this.setBlockRuntimeId(5821);
                break;
            case ORANGE:
                this.setBlockRuntimeId(5808);
                break;
            case YELLOW:
                this.setBlockRuntimeId(5811);
                break;
            case LIME:
                this.setBlockRuntimeId(5812);
                break;
            case GREEN:
                this.setBlockRuntimeId(5820);
                break;
            case CYAN:
                this.setBlockRuntimeId(5816);
                break;
            case LIGHT_BLUE:
                this.setBlockRuntimeId(5810);
                break;
            case BLUE:
                this.setBlockRuntimeId(5818);
                break;
            case PURPLE:
                this.setBlockRuntimeId(5817);
                break;
            case MAGENTA:
                this.setBlockRuntimeId(5809);
                break;
            case PINK:
                this.setBlockRuntimeId(5813);
                break;
            default:
                this.setBlockRuntimeId(5807);
                break;
        }

        return this;
    }

    public BlockColor getColor() {
        switch (this.blockRuntimeId) {
            case 5815:
                return BlockColor.SILVER;
            case 5814:
                return BlockColor.GRAY;
            case 5822:
                return BlockColor.BLACK;
            case 5819:
                return BlockColor.BROWN;
            case 5821:
                return BlockColor.RED;
            case 5808:
                return BlockColor.ORANGE;
            case 5811:
                return BlockColor.YELLOW;
            case 5812:
                return BlockColor.LIME;
            case 5820:
                return BlockColor.GREEN;
            case 5816:
                return BlockColor.CYAN;
            case 5810:
                return BlockColor.LIGHT_BLUE;
            case 5818:
                return BlockColor.BLUE;
            case 5817:
                return BlockColor.PURPLE;
            case 5809:
                return BlockColor.MAGENTA;
            case 5813:
                return BlockColor.PINK;
            default:
                return BlockColor.WHITE;
        }
    }
}
