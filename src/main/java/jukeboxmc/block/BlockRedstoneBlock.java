package jukeboxmc.block;

import org.jukeboxmc.item.ItemRedstoneBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedstoneBlock extends Block {

    public BlockRedstoneBlock() {
        super( "minecraft:redstone_block" );
    }

    @Override
    public ItemRedstoneBlock toItem() {
        return new ItemRedstoneBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_BLOCK;
    }

}
