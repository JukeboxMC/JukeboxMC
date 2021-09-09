package jukeboxmc.item;

import org.jukeboxmc.block.BlockPowderSnow;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPowderSnow extends Item{

    public ItemPowderSnow() {
        super( "minecraft:powder_snow" );
    }

    @Override
    public BlockPowderSnow getBlock() {
        return new BlockPowderSnow();
    }
}
