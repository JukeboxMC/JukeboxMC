package jukeboxmc.item;

import org.jukeboxmc.block.BlockCoral;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.CoralColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoral extends Item {

    public ItemCoral( int blockRuntimeId ) {
        super( "minecraft:coral", blockRuntimeId );
    }

    @Override
    public BlockCoral getBlock() {
        return (BlockCoral) BlockType.getBlock( this.blockRuntimeId );
    }

    public CoralColor getCoralType() {
        return this.getBlock().getCoralColor();
    }

}
