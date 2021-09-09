package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement23;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement23 extends Block {

    public BlockElement23() {
        super( "minecraft:element_23" );
    }

    @Override
    public ItemElement23 toItem() {
        return new ItemElement23();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_23;
    }

}
