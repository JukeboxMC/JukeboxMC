package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemConduit;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConduit extends BlockWaterlogable {

    public BlockConduit() {
        super( "minecraft:conduit" );
    }

    @Override
    public ItemConduit toItem() {
        return new ItemConduit();
    }

    @Override
    public BlockType getType() {
        return BlockType.CONDUIT;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
