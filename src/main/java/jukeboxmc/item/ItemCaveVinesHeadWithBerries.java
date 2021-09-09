package jukeboxmc.item;

import org.jukeboxmc.block.BlockCaveVinesHeadWithBerries;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCaveVinesHeadWithBerries extends Item {

    public ItemCaveVinesHeadWithBerries() {
        super( "minecraft:cave_vines_head_with_berries" );
    }

    @Override
    public BlockCaveVinesHeadWithBerries getBlock() {
        return new BlockCaveVinesHeadWithBerries();
    }
}
