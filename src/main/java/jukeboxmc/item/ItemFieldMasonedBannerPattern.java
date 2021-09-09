package jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFieldMasonedBannerPattern extends Item {

    public ItemFieldMasonedBannerPattern() {
        super ( "minecraft:field_masoned_banner_pattern" );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
