package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement93;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement93 extends Block {

    public BlockElement93() {
        super( "minecraft:element_93" );
    }

    @Override
    public ItemElement93 toItem() {
        return new ItemElement93();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_93;
    }

}
