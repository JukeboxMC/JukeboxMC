package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveButton;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveButton extends Block {

    public BlockMangroveButton() {
        super( "minecraft:mangrove_button" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveButton();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_BUTTON;
    }
}