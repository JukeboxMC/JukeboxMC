package jukeboxmc.item;

import org.jukeboxmc.block.BlockHayBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHayBlock extends Item {

    public ItemHayBlock() {
        super ( "minecraft:hay_block" );
    }

    @Override
    public BlockHayBlock getBlock() {
        return new BlockHayBlock();
    }
}
