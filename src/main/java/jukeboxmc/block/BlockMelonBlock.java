package jukeboxmc.block;

import org.jukeboxmc.item.ItemMelonBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMelonBlock extends Block {

    public BlockMelonBlock() {
        super( "minecraft:melon_block" );
    }

    @Override
    public ItemMelonBlock toItem() {
        return new ItemMelonBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MELON_BLOCK;
    }

}
