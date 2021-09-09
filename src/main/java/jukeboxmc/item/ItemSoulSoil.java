package jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulSoil;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulSoil extends Item{

    public ItemSoulSoil() {
        super ( "minecraft:soul_soil" );
    }

    @Override
    public BlockSoulSoil getBlock() {
        return new BlockSoulSoil();
    }
}
