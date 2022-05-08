package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemChemicalHeat;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChemicalHeat extends Block {

    public BlockChemicalHeat() {
        super( "minecraft:chemical_heat" );
    }

    @Override
    public ItemChemicalHeat toItem() {
        return new ItemChemicalHeat();
    }

    @Override
    public BlockType getType() {
        return BlockType.CHEMICAL_HEAT;
    }

}
