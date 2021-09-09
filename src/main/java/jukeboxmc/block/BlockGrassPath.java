package jukeboxmc.block;

import org.jukeboxmc.item.ItemGrassPath;

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

}
