package jukeboxmc.block;

import org.jukeboxmc.item.ItemCopperBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCopperBlock extends Block {

    public BlockCopperBlock() {
        super( "minecraft:copper_block" );
    }

    @Override
    public ItemCopperBlock toItem() {
        return new ItemCopperBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COPPER_BLOCK;
    }
}
