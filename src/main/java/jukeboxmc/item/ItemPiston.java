package jukeboxmc.item;

import org.jukeboxmc.block.BlockPiston;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPiston extends Item {

    public ItemPiston() {
        super ( "minecraft:piston" );
    }

    @Override
    public BlockPiston getBlock() {
        return new BlockPiston();
    }
}
