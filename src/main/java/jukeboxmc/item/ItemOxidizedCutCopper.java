package jukeboxmc.item;

import org.jukeboxmc.block.BlockOxidizedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOxidizedCutCopper extends Item{

    public ItemOxidizedCutCopper() {
        super( "minecraft:oxidized_cut_copper" );
    }

    @Override
    public BlockOxidizedCutCopper getBlock() {
        return new BlockOxidizedCutCopper();
    }
}
