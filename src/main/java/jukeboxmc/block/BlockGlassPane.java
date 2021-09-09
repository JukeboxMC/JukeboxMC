package jukeboxmc.block;

import org.jukeboxmc.item.ItemGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlassPane extends BlockWaterlogable {

    public BlockGlassPane() {
        super( "minecraft:glass_pane" );
    }

    @Override
    public ItemGlassPane toItem() {
        return new ItemGlassPane();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLASS_PANE;
    }

}
