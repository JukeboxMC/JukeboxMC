package jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchStandingSign extends Item {

    public ItemBirchStandingSign() {
        super ( "minecraft:birch_standing_sign" );
    }

    @Override
    public BlockBirchStandingSign getBlock() {
        return new BlockBirchStandingSign();
    }
}
