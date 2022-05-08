package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemLava;
import org.jukeboxmc.world.Dimension;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLava extends BlockLiquid {

    public BlockLava() {
        super( "minecraft:lava" );
    }

    protected BlockLava( String identifier ) {
        super( identifier );
    }

    @Override
    public ItemLava toItem() {
        return new ItemLava();
    }

    @Override
    public BlockType getType() {
        return BlockType.LAVA;
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
                this.liquidCollide( new BlockObsidian() );
            } else if ( this.getLiquidDepth() <= 4 ) {
                this.liquidCollide( new BlockCobblestone() );
            }
        }
    }

    @Override
    protected void flowIntoBlock( Block block, int newFlowDecay ) {
        if ( block instanceof BlockWater ) {
            ( (BlockLiquid) block ).liquidCollide( new BlockStone() );
        } else {
            super.flowIntoBlock( block, newFlowDecay );
        }
    }

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockLava blockLava = new BlockLava();
        blockLava.setLiquidDepth( liquidDepth );
        return blockLava;
    }
}