package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement89;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement89 extends Block {

    public BlockElement89() {
        super( "minecraft:element_89" );
    }

    @Override
    public ItemElement89 toItem() {
        return new ItemElement89();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_89;
    }

}
