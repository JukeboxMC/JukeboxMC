package jukeboxmc.block;

import org.jukeboxmc.item.ItemMossyCobblestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMossyCobblestone extends Block {

    public BlockMossyCobblestone() {
        super( "minecraft:mossy_cobblestone" );
    }

    @Override
    public ItemMossyCobblestone toItem() {
        return new ItemMossyCobblestone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOSSY_COBBLESTONE;
    }

}
