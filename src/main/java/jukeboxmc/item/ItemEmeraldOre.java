package jukeboxmc.item;

import org.jukeboxmc.block.BlockEmeraldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEmeraldOre extends Item {

    public ItemEmeraldOre() {
        super ( "minecraft:emerald_ore" );
    }

    @Override
    public BlockEmeraldOre getBlock() {
        return new BlockEmeraldOre();
    }
}
