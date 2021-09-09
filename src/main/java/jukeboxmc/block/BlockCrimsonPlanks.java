package jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonPlanks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrimsonPlanks extends Block {

    public BlockCrimsonPlanks() {
        super( "minecraft:crimson_planks" );
    }

    @Override
    public ItemCrimsonPlanks toItem() {
        return new ItemCrimsonPlanks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_PLANKS;
    }

}
