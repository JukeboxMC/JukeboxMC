package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement45;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement45 extends Block {

    public BlockElement45() {
        super( "minecraft:element_45" );
    }

    @Override
    public ItemElement45 toItem() {
        return new ItemElement45();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_45;
    }

}
