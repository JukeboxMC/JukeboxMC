package org.jukeboxmc.block;

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
    public ItemFrogSpawn toItem() {
        return new ItemFrogSpawn();
    }

    @Override
    public BlockType getType() {
        return BlockType.FROG_SPAWN;
    }
}