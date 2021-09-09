package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement116;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement116 extends Block {

    public BlockElement116() {
        super( "minecraft:element_116" );
    }

    @Override
    public ItemElement116 toItem() {
        return new ItemElement116();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_116;
    }

}
