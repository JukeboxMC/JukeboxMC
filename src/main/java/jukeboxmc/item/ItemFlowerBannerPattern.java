package jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFlowerBannerPattern extends Item {

    public ItemFlowerBannerPattern() {
        super ( "minecraft:flower_banner_pattern" );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
