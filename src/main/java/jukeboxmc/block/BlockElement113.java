package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement113;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement113 extends Block {

    public BlockElement113() {
        super( "minecraft:element_113" );
    }

    @Override
    public ItemElement113 toItem() {
        return new ItemElement113();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_113;
    }

}
