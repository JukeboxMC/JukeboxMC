package jukeboxmc.item;

import org.jukeboxmc.block.BlockFrame;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFrame extends Item {

    public ItemFrame() {
        super ( "minecraft:frame" );
    }

    @Override
    public BlockFrame getBlock() {
        return new BlockFrame();
    }
}
