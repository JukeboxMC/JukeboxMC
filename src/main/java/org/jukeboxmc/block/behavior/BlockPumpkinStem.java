package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPumpkinStem extends BlockCropsStem {

    public BlockPumpkinStem( Identifier identifier ) {
        super( identifier );
    }

    public BlockPumpkinStem( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public BlockType getFruitType() {
        return BlockType.PUMPKIN;
    }
}
