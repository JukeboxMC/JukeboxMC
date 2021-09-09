package jukeboxmc.item;

import org.jukeboxmc.block.BlockKelp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemKelpBlock extends Item {

    public ItemKelpBlock() {
        super ( "minecraft:item.kelp" );
    }

    @Override
    public BlockKelp getBlock() {
        return new BlockKelp();
    }
}
