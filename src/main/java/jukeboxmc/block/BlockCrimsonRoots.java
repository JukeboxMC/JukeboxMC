package jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrimsonRoots extends Block {

    public BlockCrimsonRoots() {
        super( "minecraft:crimson_roots" );
    }

    @Override
    public ItemCrimsonRoots toItem() {
        return new ItemCrimsonRoots();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_ROOTS;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
