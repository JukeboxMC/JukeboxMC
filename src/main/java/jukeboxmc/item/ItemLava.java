package jukeboxmc.item;

import org.jukeboxmc.block.BlockLava;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLava extends Item {

    public ItemLava() {
        super ( "minecraft:lava" );
    }

    @Override
    public BlockLava getBlock() {
        return new BlockLava();
    }
}
