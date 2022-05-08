package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemHardStainedGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHardStainedGlassPane extends Block {

    public BlockHardStainedGlassPane() {
        super( "minecraft:hard_stained_glass_pane" );
    }



    @Override
    public ItemHardStainedGlassPane toItem() {
        return new ItemHardStainedGlassPane();
    }

    @Override
    public BlockType getType() {
        return BlockType.HARD_STAINED_GLASS_PANE;
    }


    public void setColor( BlockColor color ) {
        this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
