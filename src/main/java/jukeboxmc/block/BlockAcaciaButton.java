package jukeboxmc.block;

import org.jukeboxmc.item.ItemAcaciaButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaButton extends BlockButton {

    public BlockAcaciaButton() {
        super( "minecraft:acacia_button" );
    }

    @Override
    public ItemAcaciaButton toItem() {
        return new ItemAcaciaButton();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACACIA_BUTTON;
    }
}
