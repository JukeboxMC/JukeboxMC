package jukeboxmc.item;

import org.jukeboxmc.block.BlockCopperBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCopperBlock extends Item{

    public ItemCopperBlock() {
        super( "minecraft:copper_block" );
    }

    @Override
    public BlockCopperBlock getBlock() {
        return new BlockCopperBlock();
    }
}
