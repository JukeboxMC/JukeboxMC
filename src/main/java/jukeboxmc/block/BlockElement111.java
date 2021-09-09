package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement111;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement111 extends Block {

    public BlockElement111() {
        super( "minecraft:element_111" );
    }

    @Override
    public ItemElement111 toItem() {
        return new ItemElement111();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_111;
    }

}
