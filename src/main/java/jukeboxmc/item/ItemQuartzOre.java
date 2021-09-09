package jukeboxmc.item;

import org.jukeboxmc.block.BlockQuartzOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzOre extends Item {

    public ItemQuartzOre() {
        super ( "minecraft:quartz_ore" );
    }

    @Override
    public BlockQuartzOre getBlock() {
        return new BlockQuartzOre();
    }
}
