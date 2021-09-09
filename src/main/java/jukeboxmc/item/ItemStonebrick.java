package jukeboxmc.item;

import org.jukeboxmc.block.BlockStonebrick;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.StoneBrickType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStonebrick extends Item {

    public ItemStonebrick( int blockRuntimeId ) {
        super( "minecraft:stonebrick", blockRuntimeId );
    }

    @Override
    public BlockStonebrick getBlock() {
        return (BlockStonebrick) BlockType.getBlock( this.blockRuntimeId );
    }

    public StoneBrickType getStoneType() {
        return this.getBlock().getStoneBrickType();
    }

}
