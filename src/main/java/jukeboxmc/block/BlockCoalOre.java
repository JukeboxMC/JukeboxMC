package jukeboxmc.block;

import org.jukeboxmc.item.ItemCoalOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoalOre extends Block {

    public BlockCoalOre() {
        super( "minecraft:coal_ore" );
    }

    @Override
    public ItemCoalOre toItem() {
        return new ItemCoalOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COAL_ORE;
    }

}
