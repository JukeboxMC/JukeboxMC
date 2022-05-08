package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement73;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement73 extends Block {

    public BlockElement73() {
        super( "minecraft:element_73" );
    }

    @Override
    public ItemElement73 toItem() {
        return new ItemElement73();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_73;
    }

}
