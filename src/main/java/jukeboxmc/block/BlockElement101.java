package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement101;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement101 extends Block {

    public BlockElement101() {
        super( "minecraft:element_101" );
    }

    @Override
    public ItemElement101 toItem() {
        return new ItemElement101();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_101;
    }

}
