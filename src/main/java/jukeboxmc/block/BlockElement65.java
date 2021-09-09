package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement65;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement65 extends Block {

    public BlockElement65() {
        super( "minecraft:element_65" );
    }

    @Override
    public ItemElement65 toItem() {
        return new ItemElement65();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_65;
    }

}
