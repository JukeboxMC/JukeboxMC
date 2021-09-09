package jukeboxmc.block;

import org.jukeboxmc.item.ItemHardenedClay;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHardenedClay extends Block {

    public BlockHardenedClay() {
        super( "minecraft:hardened_clay" );
    }

    @Override
    public ItemHardenedClay toItem() {
        return new ItemHardenedClay();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HARDENED_CLAY;
    }

}
