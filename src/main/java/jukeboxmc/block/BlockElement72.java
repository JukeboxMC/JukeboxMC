package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement72;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement72 extends Block {

    public BlockElement72() {
        super( "minecraft:element_72" );
    }

    @Override
    public ItemElement72 toItem() {
        return new ItemElement72();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_72;
    }

}
