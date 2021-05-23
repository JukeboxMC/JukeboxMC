package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonButton;

public class BlockCrimsonButton extends BlockButton {

    public BlockCrimsonButton() {
        super("minecraft:crimson_button");
    }

    @Override
    public ItemCrimsonButton toItem() {
        return new ItemCrimsonButton();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_BUTTON;
    }

}