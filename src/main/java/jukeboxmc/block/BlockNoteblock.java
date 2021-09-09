package jukeboxmc.block;

import org.jukeboxmc.item.ItemNoteblock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNoteblock extends Block {

    public BlockNoteblock() {
        super( "minecraft:noteblock" );
    }

    @Override
    public ItemNoteblock toItem() {
        return new ItemNoteblock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NOTEBLOCK;
    }

}
