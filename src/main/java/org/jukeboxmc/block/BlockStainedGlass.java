package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemStainedGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStainedGlass extends Block {

    public BlockStainedGlass() {
        super( "minecraft:stained_glass" );
    }

    @Override
    public ItemStainedGlass toItem() {
        return new ItemStainedGlass( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STAINED_GLASS;
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public BlockStainedGlass setColor( BlockColor color ) {
        this.setState( "color", color.name().toLowerCase() );
        return this;
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
