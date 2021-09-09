package jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstoneButton;

public class BlockPolishedBlackstoneButton extends BlockButton {

    public BlockPolishedBlackstoneButton() {
        super("minecraft:polished_blackstone_button");
    }

    @Override
    public ItemPolishedBlackstoneButton toItem() {
        return new ItemPolishedBlackstoneButton();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_BLACKSTONE_BUTTON;
    }

}