package jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakStandingSign extends Item {

    public ItemDarkOakStandingSign() {
        super ( "minecraft:darkoak_standing_sign" );
    }

    @Override
    public BlockDarkOakStandingSign getBlock() {
        return new BlockDarkOakStandingSign();
    }
}
