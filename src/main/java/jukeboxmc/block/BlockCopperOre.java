package jukeboxmc.block;

import org.jukeboxmc.item.ItemCopperOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCopperOre extends Block {

    public BlockCopperOre() {
        super( "minecraft:copper_ore" );
    }

    @Override
    public ItemCopperOre toItem() {
        return new ItemCopperOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COPPER_ORE;
    }
}
