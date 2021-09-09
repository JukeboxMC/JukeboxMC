package jukeboxmc.item;

import org.jukeboxmc.block.BlockGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlassPane extends Item {

    public ItemGlassPane() {
        super ( "minecraft:glass_pane" );
    }

    @Override
    public BlockGlassPane getBlock() {
        return new BlockGlassPane();
    }
}
