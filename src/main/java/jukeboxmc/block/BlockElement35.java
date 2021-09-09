package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement35;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement35 extends Block {

    public BlockElement35() {
        super( "minecraft:element_35" );
    }

    @Override
    public ItemElement35 toItem() {
        return new ItemElement35();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_35;
    }

}
