package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement87;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement87 extends Block {

    public BlockElement87() {
        super( "minecraft:element_87" );
    }

    @Override
    public ItemElement87 toItem() {
        return new ItemElement87();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_87;
    }

}
