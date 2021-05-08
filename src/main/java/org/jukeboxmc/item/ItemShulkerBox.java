package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockShulkerBox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemShulkerBox extends Item {

    public ItemShulkerBox() {
        super( 218, 5565 );
    }

    @Override
    public ItemType getItemType() {
        return super.getItemType();
    }

    @Override
    public BlockShulkerBox getBlock() {
        return new BlockShulkerBox().setColor( this.getColor() );
    }

    public ItemShulkerBox setColor( BlockColor blockColor ) {
        switch ( blockColor ) {
            case SILVER:
                this.setBlockRuntimeId( 5573 );
                break;
            case GRAY:
                this.setBlockRuntimeId( 5572 );
                break;
            case BLACK:
                this.setBlockRuntimeId( 5580 );
                break;
            case BROWN:
                this.setBlockRuntimeId( 5577 );
                break;
            case RED:
                this.setBlockRuntimeId( 5579 );
                break;
            case ORANGE:
                this.setBlockRuntimeId( 5566 );
                break;
            case YELLOW:
                this.setBlockRuntimeId( 5569 );
                break;
            case LIME:
                this.setBlockRuntimeId( 5570 );
                break;
            case GREEN:
                this.setBlockRuntimeId( 5578 );
                break;
            case CYAN:
                this.setBlockRuntimeId( 5574 );
                break;
            case LIGHT_BLUE:
                this.setBlockRuntimeId( 5568 );
                break;
            case BLUE:
                this.setBlockRuntimeId( 5576 );
                break;
            case PURPLE:
                this.setBlockRuntimeId( 5575 );
                break;
            case MAGENTA:
                this.setBlockRuntimeId( 5567 );
                break;
            case PINK:
                this.setBlockRuntimeId( 5571 );
                break;
            default:
                this.setBlockRuntimeId( 5565 );
                break;
        }
        return this;
    }

    public BlockColor getColor() {
        switch ( this.blockRuntimeId ) {
            case 5573:
                return BlockColor.SILVER;
            case 5572:
                return BlockColor.GRAY;
            case 5580:
                return BlockColor.BLACK;
            case 5577:
                return BlockColor.BROWN;
            case 5579:
                return BlockColor.RED;
            case 5566:
                return BlockColor.ORANGE;
            case 5569:
                return BlockColor.YELLOW;
            case 5570:
                return BlockColor.LIME;
            case 5578:
                return BlockColor.GREEN;
            case 5574:
                return BlockColor.CYAN;
            case 5568:
                return BlockColor.LIGHT_BLUE;
            case 5576:
                return BlockColor.BLUE;
            case 5575:
                return BlockColor.PURPLE;
            case 5567:
                return BlockColor.MAGENTA;
            case 5571:
                return BlockColor.PINK;
            default:
                return BlockColor.WHITE;
        }
    }
}
