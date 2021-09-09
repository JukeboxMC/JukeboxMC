package jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenButton extends Item {

    public ItemWoodenButton() {
        super ( "minecraft:wooden_button" );
    }

    @Override
    public BlockWoodenButton getBlock() {
        return new BlockWoodenButton();
    }
}
