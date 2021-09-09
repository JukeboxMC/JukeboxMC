package jukeboxmc.block;

import org.jukeboxmc.item.ItemMossBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMossBlock extends Block{

    public BlockMossBlock() {
        super( "minecraft:moss_block" );
    }

    @Override
    public ItemMossBlock toItem() {
        return new ItemMossBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOSS_BLOCK;
    }
}
