package jukeboxmc.block;

import org.jukeboxmc.item.ItemAcaciaTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaTrapdoor extends Block {

    public BlockAcaciaTrapdoor() {
        super( "minecraft:acacia_trapdoor" );
    }

    @Override
    public ItemAcaciaTrapdoor toItem() {
        return new ItemAcaciaTrapdoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACACIA_TRAPDOOR;
    }

}
