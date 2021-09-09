package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement10;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement11 extends Block {

    public BlockElement11() {
        super( "minecraft:element_11" );
    }

    @Override
    public ItemElement10 toItem() {
        return new ItemElement10();
    }

    @Override
    public BlockType getBlockType() {
        return null;
    }

}
