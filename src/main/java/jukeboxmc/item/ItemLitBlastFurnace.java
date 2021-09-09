package jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowingBlastFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLitBlastFurnace extends Item {

    public ItemLitBlastFurnace() {
        super ( "minecraft:lit_blast_furnace" );
    }

    @Override
    public BlockGlowingBlastFurnace getBlock() {
        return new BlockGlowingBlastFurnace();
    }
}
