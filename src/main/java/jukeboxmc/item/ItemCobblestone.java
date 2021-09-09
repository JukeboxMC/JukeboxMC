package jukeboxmc.item;

import org.jukeboxmc.block.BlockCobblestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobblestone extends Item {

    public ItemCobblestone() {
        super ( "minecraft:cobblestone" );
    }

    @Override
    public BlockCobblestone getBlock() {
        return new BlockCobblestone();
    }
}
