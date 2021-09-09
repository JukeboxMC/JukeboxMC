package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemWool;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWool extends Block {

    public BlockWool() {
        super( "minecraft:wool" );
    }



    @Override
    public ItemWool toItem() {
        return new ItemWool( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOOL;
    }

    public BlockWool setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
