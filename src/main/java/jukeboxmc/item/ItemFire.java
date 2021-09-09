package jukeboxmc.item;

import org.jukeboxmc.block.BlockFire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFire extends Item {

    public ItemFire() {
        super ( "minecraft:fire" );
    }

    @Override
    public BlockFire getBlock() {
        return new BlockFire();
    }
}
