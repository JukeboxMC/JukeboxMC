package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement71;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement71 extends Block {

    public BlockElement71() {
        super( "minecraft:element_71" );
    }

    @Override
    public ItemElement71 toItem() {
        return new ItemElement71();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_71;
    }

}
