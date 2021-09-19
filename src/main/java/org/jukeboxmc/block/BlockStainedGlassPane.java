package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemStainedGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStainedGlassPane extends Block {

    public BlockStainedGlassPane() {
        super( "minecraft:stained_glass_pane" );
    }

    @Override
    public ItemStainedGlassPane toItem() {
        return new ItemStainedGlassPane( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STAINED_GLASS_PANE;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public BlockStainedGlassPane setColor( BlockColor color ) {
        this.setState( "color", color.name().toLowerCase() );
        return this;
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }

}
