package jukeboxmc.item;

import org.jukeboxmc.block.BlockLava;
import org.jukeboxmc.item.behavior.ItemBucketBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLavaBucket extends ItemBucketBehavior {

    public ItemLavaBucket() {
        super ( "minecraft:lava_bucket" );
    }

    @Override
    public BlockLava getBlock() {
        return new BlockLava();
    }
}
