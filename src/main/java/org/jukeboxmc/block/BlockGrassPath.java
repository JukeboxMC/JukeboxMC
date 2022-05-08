package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGrassPath;
import org.jukeboxmc.item.type.ItemToolType;

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
    public BlockType getType() {
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
