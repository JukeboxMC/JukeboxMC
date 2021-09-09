package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement52;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement52 extends Block {

    public BlockElement52() {
        super( "minecraft:element_52" );
    }

    @Override
    public ItemElement52 toItem() {
        return new ItemElement52();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_52;
    }

}
