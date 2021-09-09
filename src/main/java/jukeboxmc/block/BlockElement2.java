package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement2 extends Block {

    public BlockElement2() {
        super( "minecraft:element_2" );
    }

    @Override
    public ItemElement2 toItem() {
        return new ItemElement2();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_2;
    }

}
