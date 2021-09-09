package jukeboxmc.block;

import org.jukeboxmc.item.ItemBlueIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBlueIce extends Block {

    public BlockBlueIce() {
        super( "minecraft:blue_ice" );
    }

    @Override
    public ItemBlueIce toItem() {
        return new ItemBlueIce();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLUE_ICE;
    }

}
