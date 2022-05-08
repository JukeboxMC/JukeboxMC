package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedButton;

public class BlockWarpedButton extends BlockButton {

    public BlockWarpedButton() {
        super("minecraft:warped_button");
    }

    @Override
    public ItemWarpedButton toItem() {
        return new ItemWarpedButton();
    }

    @Override
    public BlockType getType() {
        return BlockType.WARPED_BUTTON;
    }

}