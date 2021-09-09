package jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedPlanks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedPlanks extends Block {

    public BlockWarpedPlanks() {
        super( "minecraft:warped_planks" );
    }

    @Override
    public ItemWarpedPlanks toItem() {
        return new ItemWarpedPlanks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_PLANKS;
    }

}
