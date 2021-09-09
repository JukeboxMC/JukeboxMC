package jukeboxmc.block;

import org.jukeboxmc.item.ItemLapisBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLapisBlock extends Block {

    public BlockLapisBlock() {
        super( "minecraft:lapis_block" );
    }

    @Override
    public ItemLapisBlock toItem() {
        return new ItemLapisBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LAPIS_BLOCK;
    }

}
