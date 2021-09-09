package jukeboxmc.item;

import org.jukeboxmc.block.BlockDripstoneBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDripstoneBlock extends Item{

    public ItemDripstoneBlock() {
        super( "minecraft:dripstone_block" );
    }

    @Override
    public BlockDripstoneBlock getBlock() {
        return new BlockDripstoneBlock();
    }
}
