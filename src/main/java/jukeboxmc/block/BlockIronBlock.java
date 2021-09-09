package jukeboxmc.block;

import org.jukeboxmc.item.ItemIronBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronBlock extends Block {

    public BlockIronBlock() {
        super( "minecraft:iron_block" );
    }

    @Override
    public ItemIronBlock toItem() {
        return new ItemIronBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.IRON_BLOCK;
    }

}
