package jukeboxmc.item;

import org.jukeboxmc.block.BlockStainedGlassPane;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedGlassPane extends Item {

    public ItemStainedGlassPane( int blockRuntimeId ) {
        super( "minecraft:stained_glass_pane", blockRuntimeId );
    }

    @Override
    public ItemType getItemType() {
        switch ( this.getColor() ) {
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
        return (BlockStainedGlassPane) BlockType.getBlock( this.blockRuntimeId );
    }


    public BlockColor getColor() {
        return this.getBlock().getColor();
    }
}
