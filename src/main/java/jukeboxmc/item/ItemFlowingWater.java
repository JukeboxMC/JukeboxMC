package jukeboxmc.item;

import org.jukeboxmc.block.BlockFlowingWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFlowingWater extends Item {

    public ItemFlowingWater() {
        super ( "minecraft:flowing_water" );
    }

    @Override
    public BlockFlowingWater getBlock() {
        return new BlockFlowingWater();
    }
}
