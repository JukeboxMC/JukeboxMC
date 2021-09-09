package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement70;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement70 extends Block {

    public BlockElement70() {
        super( "minecraft:element_70" );
    }

    @Override
    public ItemElement70 toItem() {
        return new ItemElement70();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_70;
    }

}
