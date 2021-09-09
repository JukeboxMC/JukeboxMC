package jukeboxmc.block;

import org.jukeboxmc.item.ItemDiamondOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDiamondOre extends Block {

    public BlockDiamondOre() {
        super( "minecraft:diamond_ore" );
    }

    @Override
    public ItemDiamondOre toItem() {
        return new ItemDiamondOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DIAMOND_ORE;
    }

}
