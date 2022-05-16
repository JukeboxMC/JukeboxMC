package org.jukeboxmc.block;

import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public abstract class BlockFroglight extends Block {

    public BlockFroglight( String identifier ) {
        super( identifier );
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}