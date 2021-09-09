package jukeboxmc.item;

import org.jukeboxmc.block.BlockChiseledDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChiseledDeepslate extends Item{

    public ItemChiseledDeepslate() {
        super( "minecraft:chiseled_deepslate" );
    }

    @Override
    public BlockChiseledDeepslate getBlock() {
        return new BlockChiseledDeepslate();
    }
}
