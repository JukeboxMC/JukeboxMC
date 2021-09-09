package jukeboxmc.item;

import org.jukeboxmc.block.BlockLightBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLightBlock extends Item {

    public ItemLightBlock() {
        super ( "minecraft:light_block" );
    }

    @Override
    public BlockLightBlock getBlock() {
        return new BlockLightBlock();
    }
}
