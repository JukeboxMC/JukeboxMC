package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement63;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement63 extends Block {

    public BlockElement63() {
        super( "minecraft:element_63" );
    }

    @Override
    public ItemElement63 toItem() {
        return new ItemElement63();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_63;
    }

}
