package jukeboxmc.item;

import org.jukeboxmc.block.BlockShulkerBox;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemShulkerBox extends Item {

    public ItemShulkerBox( int blockRuntimeId ) {
        super( "minecraft:shulker_box", blockRuntimeId );
    }

    @Override
    public ItemType getItemType() {
        switch ( this.getColor() ) {
            case SILVER:
                return ItemType.SILVER_SHULKER_BOX;
            case GRAY:
                return ItemType.GRAY_SHULKER_BOX;
            case BLACK:
                return ItemType.BLACK_SHULKER_BOX;
            case BROWN:
                return ItemType.BROWN_SHULKER_BOX;
            case RED:
                return ItemType.RED_SHULKER_BOX;
            case ORANGE:
                return ItemType.ORANGE_SHULKER_BOX;
            case YELLOW:
                return ItemType.YELLOW_SHULKER_BOX;
            case LIME:
                return ItemType.LIME_SHULKER_BOX;
            case GREEN:
                return ItemType.GREEN_SHULKER_BOX;
            case CYAN:
                return ItemType.CYAN_SHULKER_BOX;
            case LIGHT_BLUE:
                return ItemType.LIGHT_BLUE_SHULKER_BOX;
            case BLUE:
                return ItemType.BLUE_SHULKER_BOX;
            case PURPLE:
                return ItemType.PURPLE_SHULKER_BOX;
            case MAGENTA:
                return ItemType.MAGENTA_SHULKER_BOX;
            case PINK:
                return ItemType.PINK_SHULKER_BOX;
            default:
                return ItemType.WHITE_SHULKER_BOX;
        }
    }

    @Override
    public BlockShulkerBox getBlock() {
        return (BlockShulkerBox) BlockType.getBlock( this.blockRuntimeId );
    }

    public BlockColor getColor() {
        return this.getBlock().getColor();
    }
}
