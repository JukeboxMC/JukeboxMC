package jukeboxmc.block;

import org.jukeboxmc.item.ItemElement81;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement81 extends Block {

    public BlockElement81() {
        super( "minecraft:element_81" );
    }

    @Override
    public ItemElement81 toItem() {
        return new ItemElement81();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_81;
    }

}
