package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMobSpawner;
import org.jukeboxmc.item.type.ItemToolType;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMobSpawner extends BlockWaterlogable {

    public BlockMobSpawner() {
        super( "minecraft:mob_spawner" );
    }

    @Override
    public ItemMobSpawner toItem() {
        return new ItemMobSpawner();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOB_SPAWNER;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return Collections.emptyList();
    }
}
