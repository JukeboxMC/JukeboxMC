package jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherWartBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherWartBlock extends Block {

    public BlockNetherWartBlock() {
        super( "minecraft:nether_wart_block" );
    }

    @Override
    public ItemNetherWartBlock toItem() {
        return new ItemNetherWartBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_WART_BLOCK;
    }

}
