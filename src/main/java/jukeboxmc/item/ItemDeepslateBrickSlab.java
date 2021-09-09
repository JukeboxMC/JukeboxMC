package jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateBrickSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateBrickSlab extends Item{

    public ItemDeepslateBrickSlab() {
        super( "minecraft:deepslate_brick_slab" );
    }

    @Override
    public BlockDeepslateBrickSlab getBlock() {
        return new BlockDeepslateBrickSlab();
    }
}
