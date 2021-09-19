package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGrassPath;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrassPath extends Block {

    public BlockGrassPath() {
        super( "minecraft:grass_path" );
    }

    @Override
    public ItemGrassPath toItem() {
        return new ItemGrassPath();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GRASS_PATH;
    }

    @Override
    public double getHardness() {
        return 0.65;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }
}
