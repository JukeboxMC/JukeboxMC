package jukeboxmc.item;

import org.jukeboxmc.block.BlockCaveVinesBodyWithBerries;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCaveVinesBodyWithBerries extends Item{

    public ItemCaveVinesBodyWithBerries() {
        super( "minecraft:cave_vines_body_with_berries" );
    }

    @Override
    public BlockCaveVinesBodyWithBerries getBlock() {
        return new BlockCaveVinesBodyWithBerries();
    }
}
