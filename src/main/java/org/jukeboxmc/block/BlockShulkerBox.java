package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemShulkerBox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockShulkerBox extends Block {

    public BlockShulkerBox() {
        super( "minecraft:shulker_box" );
    }



    @Override
    public ItemShulkerBox toItem() {
        return new ItemShulkerBox( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SHULKER_BOX;
    }

    public BlockShulkerBox setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
