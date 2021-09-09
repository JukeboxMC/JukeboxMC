package jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaDoor extends Item {

    public ItemAcaciaDoor() {
        super( "minecraft:acacia_door" );
    }

    @Override
    public BlockAcaciaDoor getBlock() {
        return new BlockAcaciaDoor();
    }
}
