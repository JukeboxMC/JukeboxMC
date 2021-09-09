package jukeboxmc.block;

import org.jukeboxmc.item.ItemTuff;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTuff extends Block {

    public BlockTuff() {
        super( "minecraft:tuff" );
    }

    @Override
    public ItemTuff toItem() {
        return new ItemTuff();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TUFF;
    }
}
