package jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulCampfireBlock extends Item {

    public ItemSoulCampfireBlock() {
        super ( "minecraft:item.soul_campfire" );
    }

    @Override
    public BlockSoulCampfire getBlock() {
        return new BlockSoulCampfire();
    }
}
