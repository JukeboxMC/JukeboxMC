package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChemicalHeat;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChemicalHeat extends Item {

    public ItemChemicalHeat() {
        super( "minecraft:chemical_heat", 192 );
    }

    @Override
    public BlockChemicalHeat getBlock() {
        return new BlockChemicalHeat();
    }
}
