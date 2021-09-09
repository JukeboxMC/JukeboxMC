package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement9;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement9 extends Block {

    public BlockElement9() {
        super( "minecraft:element_9" );
    }

    @Override
    public ItemElement9 toItem() {
        return new ItemElement9();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_9;
    }

}
