package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBeacon;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBeacon extends BlockWaterlogable {

    public BlockBeacon() {
        super( "minecraft:beacon" );
    }

    @Override
    public ItemBeacon toItem() {
        return new ItemBeacon();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEACON;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
