package jukeboxmc.block;

import org.jukeboxmc.item.ItemClay;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockClay extends Block {

    public BlockClay() {
        super( "minecraft:clay" );
    }

    @Override
    public ItemClay toItem() {
        return new ItemClay();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CLAY;
    }

}
