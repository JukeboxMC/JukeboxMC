package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMediumAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMediumAmethystBud extends Item{

    public ItemMediumAmethystBud() {
        super( "minecraft:medium_amethyst_bud" );
    }

    @Override
    public BlockMediumAmethystBud getBlock() {
        return new BlockMediumAmethystBud();
    }
}
