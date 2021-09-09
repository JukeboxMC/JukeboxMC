package jukeboxmc.item;

import org.jukeboxmc.block.BlockEnderChest;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEnderChest extends Item {

    public ItemEnderChest() {
        super ( "minecraft:ender_chest" );
    }

    @Override
    public BlockEnderChest getBlock() {
        return new BlockEnderChest();
    }
}
