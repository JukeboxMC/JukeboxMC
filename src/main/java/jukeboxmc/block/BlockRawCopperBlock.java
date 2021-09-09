package jukeboxmc.block;

import org.jukeboxmc.item.ItemRawCopperBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRawCopperBlock extends Block {

    public BlockRawCopperBlock() {
        super( "minecraft:raw_copper_block" );
    }

    @Override
    public ItemRawCopperBlock toItem() {
        return new ItemRawCopperBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RAW_COPPER_BLOCK;
    }
}
