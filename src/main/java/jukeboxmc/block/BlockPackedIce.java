package jukeboxmc.block;

import org.jukeboxmc.item.ItemPackedIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPackedIce extends Block {

    public BlockPackedIce() {
        super( "minecraft:packed_ice" );
    }

    @Override
    public ItemPackedIce toItem() {
        return new ItemPackedIce();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PACKED_ICE;
    }

}
