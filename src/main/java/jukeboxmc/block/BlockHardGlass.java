package jukeboxmc.block;

import org.jukeboxmc.item.ItemHardGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHardGlass extends Block {

    public BlockHardGlass() {
        super( "minecraft:hard_glass" );
    }

    @Override
    public ItemHardGlass toItem() {
        return new ItemHardGlass();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HARD_GLASS;
    }

}
