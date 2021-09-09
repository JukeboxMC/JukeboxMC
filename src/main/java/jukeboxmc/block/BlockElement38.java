package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement38;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement38 extends Block {

    public BlockElement38() {
        super( "minecraft:element_38" );
    }

    @Override
    public ItemElement38 toItem() {
        return new ItemElement38();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_38;
    }

}
