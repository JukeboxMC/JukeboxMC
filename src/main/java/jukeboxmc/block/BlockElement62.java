package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement62;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement62 extends Block {

    public BlockElement62() {
        super( "minecraft:element_62" );
    }

    @Override
    public ItemElement62 toItem() {
        return new ItemElement62();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_62;
    }

}
