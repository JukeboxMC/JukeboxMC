package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.block.BlockType;
import com.dfsek.terra.api.block.state.BlockState;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraBlockType(Block block) implements BlockType {

    @Override
    public BlockState getDefaultState() {
        return new BlockStateDelegate( block );
    }

    @Override
    public boolean isSolid() {
        return block.isSolid();
    }

    @Override
    public boolean isWater() {
        return block instanceof BlockWater;
    }

    @Override
    public Block getHandle() {
        return block;
    }
}
