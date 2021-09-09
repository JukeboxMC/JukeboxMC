package jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleSign extends Item {

    public ItemJungleSign() {
        super ( "minecraft:jungle_sign" );
    }

    @Override
    public BlockJungleStandingSign getBlock() {
        return new BlockJungleStandingSign();
    }
}
