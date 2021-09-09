package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement53;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement53 extends Block {

    public BlockElement53() {
        super( "minecraft:element_53" );
    }

    @Override
    public ItemElement53 toItem() {
        return new ItemElement53();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_53;
    }

}
