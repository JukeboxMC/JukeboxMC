package jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralFan;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.CoralColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFan extends Item {

    public ItemCoralFan( int blockRuntimeId ) {
        super( "minecraft:coral_fan", blockRuntimeId );
    }

    @Override
    public BlockCoralFan getBlock() {
        return (BlockCoralFan) BlockType.getBlock( this.blockRuntimeId );
    }

    public CoralColor getCoralType() {
        return this.getBlock().getCoralColor();
    }

}
