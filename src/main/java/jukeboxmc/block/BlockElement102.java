package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement102;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement102 extends Block {

    public BlockElement102() {
        super( "minecraft:element_102" );
    }

    @Override
    public ItemElement102 toItem() {
        return new ItemElement102();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_102;
    }

}
