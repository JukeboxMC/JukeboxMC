package jukeboxmc.block;

import org.jukeboxmc.item.ItemWoodenButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWoodenButton extends BlockButton {

    public BlockWoodenButton() {
        super( "minecraft:wooden_button" );
    }

    @Override
    public ItemWoodenButton toItem() {
        return new ItemWoodenButton();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_BUTTON;
    }

}
