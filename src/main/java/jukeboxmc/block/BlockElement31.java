package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement31;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement31 extends Block {

    public BlockElement31() {
        super( "minecraft:element_31" );
    }

    @Override
    public ItemElement31 toItem() {
        return new ItemElement31();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_31;
    }

}
