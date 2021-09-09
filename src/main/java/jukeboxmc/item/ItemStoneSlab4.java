package jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneSlab4;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.StoneSlab4Type;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab4 extends Item {

    public ItemStoneSlab4( int blockRuntimeId ) {
        super( "minecraft:double_stone_slab4", blockRuntimeId );
    }

    @Override
    public BlockStoneSlab4 getBlock() {
        return (BlockStoneSlab4) BlockType.getBlock( this.blockRuntimeId );
    }

    public StoneSlab4Type getSlabType() {
        return this.getBlock().getStoneSlabType();
    }

}
