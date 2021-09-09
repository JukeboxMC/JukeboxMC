package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement19;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement19 extends Block {

    public BlockElement19() {
        super( "minecraft:element_19" );
    }

    @Override
    public ItemElement19 toItem() {
        return new ItemElement19();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_19;
    }

}
