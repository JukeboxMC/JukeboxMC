package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWater extends BlockLiquid {

    public BlockWater( Identifier identifier ) {
        super( identifier );
    }

    public BlockWater( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public int getTickRate() {
        return 5;
    }

    @Override
    public boolean usesWaterLogging() {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockWater blockWater = Block.create( BlockType.WATER );
        blockWater.setLiquidDepth( liquidDepth );
        return blockWater;
    }
}
