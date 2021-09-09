package jukeboxmc.item;

import org.jukeboxmc.block.BlockSlime;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSlime extends Item {

    public ItemSlime() {
        super ( "minecraft:slime" );
    }

    @Override
    public BlockSlime getBlock() {
        return new BlockSlime();
    }
}
