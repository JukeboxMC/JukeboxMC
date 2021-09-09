package jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulFire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulFire extends Item {

    public ItemSoulFire() {
        super ( "minecraft:soul_fire" );
    }

    @Override
    public BlockSoulFire getBlock() {
        return new BlockSoulFire();
    }
}
