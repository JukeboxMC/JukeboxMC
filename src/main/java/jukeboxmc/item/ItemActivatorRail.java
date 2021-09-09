package jukeboxmc.item;

import org.jukeboxmc.block.BlockActivatorRail;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemActivatorRail extends Item {

    public ItemActivatorRail() {
        super( "minecraft:activator_rail" );
    }

    @Override
    public BlockActivatorRail getBlock() {
        return new BlockActivatorRail();
    }
}
