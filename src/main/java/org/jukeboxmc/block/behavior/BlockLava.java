package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Dimension;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLava extends BlockLiquid {

    public BlockLava( Identifier identifier ) {
        super( identifier );
    }

    public BlockLava( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public int getFlowDecayPerBlock() {
        if ( this.location.getDimension() == Dimension.NETHER ) {
            return 1;
        }
        return 2;
    }

    @Override
    public int getTickRate() {
        if ( this.location.getDimension() == Dimension.NETHER ) {
            return 10;
        }
        return 30;
    }

    @Override
    protected void checkForHarden() {
        Block colliding = null;
        for ( BlockFace value : BlockFace.values() ) {
            if ( value == BlockFace.DOWN ) continue;
            Block blockSide = this.getSide( value );
            if ( blockSide instanceof BlockWater ) {
                colliding = blockSide;
                break;
            }
        }
        if ( colliding != null ) {
            if ( this.getLiquidDepth() == 0 ) {
                this.liquidCollide( Block.create( BlockType.OBSIDIAN ) );
            } else if ( this.getLiquidDepth() <= 4 ) {
                this.liquidCollide( Block.create( BlockType.COBBLESTONE ) );
            }
        }
    }

    @Override
    protected void flowIntoBlock(@NotNull Block block, int newFlowDecay ) {
        if ( block instanceof BlockWater ) {
            ( (BlockLiquid) block ).liquidCollide( Block.create( BlockType.STONE ) );
        } else {
            super.flowIntoBlock( block, newFlowDecay );
        }
    }

    @Override
    public @NotNull BlockLiquid getBlock(int liquidDepth ) {
        BlockLava blockLava = Block.create( BlockType.LAVA );
        blockLava.setLiquidDepth( liquidDepth );
        return blockLava;
    }
}
