package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement20;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement20 extends Block {

    public BlockElement20() {
        super( "minecraft:element_20" );
    }

    @Override
    public ItemElement20 toItem() {
        return new ItemElement20();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_20;
    }

}
