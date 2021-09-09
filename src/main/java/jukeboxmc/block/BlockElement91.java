package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement91;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement91 extends Block {

    public BlockElement91() {
        super( "minecraft:element_91" );
    }

    @Override
    public ItemElement91 toItem() {
        return new ItemElement91();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_91;
    }

}
