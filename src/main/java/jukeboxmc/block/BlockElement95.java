package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement95;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement95 extends Block {

    public BlockElement95() {
        super( "minecraft:element_95" );
    }

    @Override
    public ItemElement95 toItem() {
        return new ItemElement95();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_95;
    }

}
