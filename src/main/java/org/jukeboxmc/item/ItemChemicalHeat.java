package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
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
    public Block getBlock() {
        return new BlockChemicalHeat();
    }
}
