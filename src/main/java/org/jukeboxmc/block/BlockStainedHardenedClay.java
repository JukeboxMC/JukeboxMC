package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemStainedHardenedClay;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStainedHardenedClay extends Block {

    public BlockStainedHardenedClay() {
        super( "minecraft:stained_hardened_clay" );
    }

    @Override
    public ItemStainedHardenedClay toItem() {
        return new ItemStainedHardenedClay(this.runtimeId);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STAINED_HARDENED_CLAY;
    }


    public BlockStainedHardenedClay setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
