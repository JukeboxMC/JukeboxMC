package jukeboxmc.item;

import org.jukeboxmc.block.BlockIronBars;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronBars extends Item {

    public ItemIronBars() {
        super ( "minecraft:iron_bars" );
    }

    @Override
    public BlockIronBars getBlock() {
        return new BlockIronBars();
    }
}
