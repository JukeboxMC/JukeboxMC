package jukeboxmc.block;

import org.jukeboxmc.item.ItemIronBars;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronBars extends BlockWaterlogable {

    public BlockIronBars() {
        super( "minecraft:iron_bars" );
    }

    @Override
    public ItemIronBars toItem() {
        return new ItemIronBars();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.IRON_BARS;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
