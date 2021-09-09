package jukeboxmc.item;

import org.jukeboxmc.block.BlockAncientDebris;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAncientDebris extends Item {

    public ItemAncientDebris() {
        super ( "minecraft:ancient_debris" );
    }

    @Override
    public BlockAncientDebris getBlock() {
        return new BlockAncientDebris();
    }
}
