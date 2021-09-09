package jukeboxmc.block;

import org.jukeboxmc.item.ItemHangingRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHangingRoots extends Block{

    public BlockHangingRoots() {
        super( "minecraft:hanging_roots" );
    }

    @Override
    public ItemHangingRoots toItem() {
        return new ItemHangingRoots();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HANGING_ROOTS;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
