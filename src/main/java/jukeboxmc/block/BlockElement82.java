package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement82;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement82 extends Block {

    public BlockElement82() {
        super( "minecraft:element_82" );
    }

    @Override
    public ItemElement82 toItem() {
        return new ItemElement82();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_82;
    }

}
