package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement67;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement67 extends Block {

    public BlockElement67() {
        super( "minecraft:element_67" );
    }

    @Override
    public ItemElement67 toItem() {
        return new ItemElement67();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_67;
    }

}
