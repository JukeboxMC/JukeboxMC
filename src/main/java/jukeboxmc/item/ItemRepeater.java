package jukeboxmc.item;

import org.jukeboxmc.block.BlockRepeater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRepeater extends Item {

    public ItemRepeater() {
        super ( "minecraft:repeater" );
    }

    @Override
    public BlockRepeater getBlock() {
        return new BlockRepeater();
    }
}
