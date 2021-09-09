package jukeboxmc.block;

import org.jukeboxmc.item.ItemUndyedShulkerBox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockUndyedShulkerBox extends Block {

    public BlockUndyedShulkerBox() {
        super( "minecraft:undyed_shulker_box" );
    }

    @Override
    public ItemUndyedShulkerBox toItem() {
        return new ItemUndyedShulkerBox();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.UNDYED_SHULKER_BOX;
    }

}
