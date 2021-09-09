package jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCreeperBannerPattern extends Item {

    public ItemCreeperBannerPattern() {
        super ( "minecraft:creeper_banner_pattern" );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
