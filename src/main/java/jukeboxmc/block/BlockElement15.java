package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement15;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement15 extends Block {

    public BlockElement15() {
        super( "minecraft:element_15" );
    }

    @Override
    public ItemElement15 toItem() {
        return new ItemElement15();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_15;
    }

}
