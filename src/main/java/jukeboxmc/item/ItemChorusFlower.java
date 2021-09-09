package jukeboxmc.item;

import org.jukeboxmc.block.BlockChorusFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChorusFlower extends Item {

    public ItemChorusFlower() {
        super ( "minecraft:chorus_flower" );
    }

    @Override
    public BlockChorusFlower getBlock() {
        return new BlockChorusFlower();
    }
}
