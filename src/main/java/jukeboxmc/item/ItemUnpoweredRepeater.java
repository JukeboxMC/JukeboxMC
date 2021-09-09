package jukeboxmc.item;

import org.jukeboxmc.block.BlockRepeater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnpoweredRepeater extends Item {

    public ItemUnpoweredRepeater() {
        super ( "minecraft:unpowered_repeater" );
    }

    @Override
    public BlockRepeater getBlock() {
        return new BlockRepeater();
    }
}
