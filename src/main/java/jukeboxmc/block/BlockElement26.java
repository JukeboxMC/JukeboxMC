package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement26;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement26 extends Block {

    public BlockElement26() {
        super( "minecraft:element_26" );
    }

    @Override
    public ItemElement26 toItem() {
        return new ItemElement26();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_26;
    }

}
