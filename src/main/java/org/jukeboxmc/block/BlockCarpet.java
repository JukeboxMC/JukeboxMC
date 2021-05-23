package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemCarpet;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCarpet extends Block {

    public BlockCarpet() {
        super( "minecraft:carpet" );
    }



    @Override
    public ItemCarpet toItem() {
        return new ItemCarpet( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CARPET;
    }

    public BlockCarpet setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
