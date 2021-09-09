package jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherBrickBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherBrickBlock extends Item {

    public ItemNetherBrickBlock() {
        super ( "minecraft:item.nether_brick" );
    }

    @Override
    public BlockNetherBrickBlock getBlock() {
        return new BlockNetherBrickBlock();
    }
}
