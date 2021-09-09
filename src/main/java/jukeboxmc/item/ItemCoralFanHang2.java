package jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralFanHang2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanHang2 extends Item {

    public ItemCoralFanHang2() {
        super ( "minecraft:coral_fan_hang2" );
    }

    @Override
    public BlockCoralFanHang2 getBlock() {
        return new BlockCoralFanHang2();
    }
}
