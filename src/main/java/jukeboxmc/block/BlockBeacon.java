package jukeboxmc.block;

import org.jukeboxmc.item.ItemBeacon;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBeacon extends BlockWaterlogable {

    public BlockBeacon() {
        super( "minecraft:beacon" );
    }

    @Override
    public ItemBeacon toItem() {
        return new ItemBeacon();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEACON;
    }

}
