package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement22;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement22 extends Block {

    public BlockElement22() {
        super( "minecraft:element_22" );
    }

    @Override
    public ItemElement22 toItem() {
        return new ItemElement22();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_22;
    }

}
