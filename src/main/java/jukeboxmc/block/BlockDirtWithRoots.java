package jukeboxmc.block;

import org.jukeboxmc.item.ItemDirtWithRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDirtWithRoots extends Block {

    public BlockDirtWithRoots() {
        super( "minecraft:dirt_with_roots" );
    }

    @Override
    public ItemDirtWithRoots toItem() {
        return new ItemDirtWithRoots();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DIRT_WITH_ROOTS;
    }
}
