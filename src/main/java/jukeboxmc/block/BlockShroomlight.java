package jukeboxmc.block;

import org.jukeboxmc.item.ItemShroomlight;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockShroomlight extends Block {

    public BlockShroomlight() {
        super( "minecraft:shroomlight" );
    }

    @Override
    public ItemShroomlight toItem() {
        return new ItemShroomlight();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SHROOMLIGHT;
    }

}
