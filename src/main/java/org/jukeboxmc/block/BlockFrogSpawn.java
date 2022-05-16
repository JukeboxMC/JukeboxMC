package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemFrogSpawn;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockFrogSpawn extends Block {

    public BlockFrogSpawn() {
        super( "minecraft:frog_spawn" );
    }

    @Override
    public Item toItem() {
        return new ItemFrogSpawn();
    }

    @Override
    public BlockType getType() {
        return BlockType.FROG_SPAWN;
    }

    @Override
    public double getHardness() {
        return 0;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}