package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStandingBanner extends BlockBanner {

    public BlockStandingBanner() {
        super( "minecraft:standing_banner" );
    }

    @Override
    public ItemStandingBanner toItem() {
        return new ItemStandingBanner();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STANDING_BANNER;
    }

}
