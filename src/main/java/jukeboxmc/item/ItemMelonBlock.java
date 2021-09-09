package jukeboxmc.item;

import org.jukeboxmc.block.BlockMelonBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMelonBlock extends Item {

    public ItemMelonBlock() {
        super ( "minecraft:melon_block" );
    }

    @Override
    public BlockMelonBlock getBlock() {
        return new BlockMelonBlock();
    }
}
