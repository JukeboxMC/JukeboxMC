package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMelonStem extends BlockCropsStem {

    public BlockMelonStem( Identifier identifier ) {
        super( identifier );
    }

    public BlockMelonStem( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public BlockType getFruitType() {
        return BlockType.MELON_BLOCK;
    }
}
