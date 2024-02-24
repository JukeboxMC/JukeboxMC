package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.ChemistryTableType
import org.jukeboxmc.api.block.data.Direction

interface ChemistryTable : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): ChemistryTable

   fun getChemistryTableType(): ChemistryTableType

   fun setChemistryTableType(value: ChemistryTableType): ChemistryTable

}