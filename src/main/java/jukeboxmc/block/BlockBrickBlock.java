package jukeboxmc.block;

import org.jukeboxmc.item.ItemBrickBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrickBlock extends Block {

    public BlockBrickBlock() {
        super( "minecraft:brick_block" );
    }

    @Override
    public ItemBrickBlock toItem() {
        return new ItemBrickBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BRICK_BLOCK;
    }

}
