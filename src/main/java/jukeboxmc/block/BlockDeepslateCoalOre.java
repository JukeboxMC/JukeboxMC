package jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateCoalOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateCoalOre extends Block {

    public BlockDeepslateCoalOre() {
        super( "minecraft:deepslate_coal_ore" );
    }

    @Override
    public ItemDeepslateCoalOre toItem() {
        return new ItemDeepslateCoalOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_COAL_ORE;
    }
}
