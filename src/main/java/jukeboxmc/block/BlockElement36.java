package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement36;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement36 extends Block {

    public BlockElement36() {
        super( "minecraft:element_36" );
    }

    @Override
    public ItemElement36 toItem() {
        return new ItemElement36();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_36;
    }

}
