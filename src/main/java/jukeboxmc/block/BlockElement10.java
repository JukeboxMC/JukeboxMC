package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement10;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement10 extends Block {

    public BlockElement10() {
        super( "minecraft:element_10" );
    }

    @Override
    public ItemElement10 toItem() {
        return new ItemElement10();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_10;
    }

}
