package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement92;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement92 extends Block {

    public BlockElement92() {
        super( "minecraft:element_92" );
    }

    @Override
    public ItemElement92 toItem() {
        return new ItemElement92();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_92;
    }

}
