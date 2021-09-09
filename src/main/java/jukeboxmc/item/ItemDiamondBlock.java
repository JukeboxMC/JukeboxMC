package jukeboxmc.item;

import org.jukeboxmc.block.BlockDiamondBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDiamondBlock extends Item {

    public ItemDiamondBlock() {
        super( "minecraft:diamond_block" );
    }

    @Override
    public BlockDiamondBlock getBlock() {
        return new BlockDiamondBlock();
    }
}
