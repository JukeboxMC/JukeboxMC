package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockKelp extends Block {

    public BlockKelp( Identifier identifier ) {
        super( identifier );
    }

    public BlockKelp( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition );
        if ( block.getType().equals( BlockType.WATER ) ) {
            Block blockDown = block.getSide( BlockFace.DOWN );
            if ( blockDown.isSolid() || blockDown.getType().equals( BlockType.KELP ) ) {
                world.setBlock( placePosition, this, 0 );
                world.setBlock( placePosition, block, 1 );
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockBreak( Vector breakPosition ) {
        World world = this.getWorld();
        Block block = world.getBlock( breakPosition, 1 );
        if ( block instanceof BlockWater ) {
            world.setBlock( breakPosition, block, 0 );
            world.setBlock( breakPosition, Block.create( BlockType.AIR ), 1 );
            return;
        }
        super.onBlockBreak( breakPosition );
    }

    public BlockKelp setKelpAge( int value ) {
        this.setState( "kelp_age", value );
        return this;
    }

    public int getKelpAge() {
        return this.stateExists( "kelp_age" ) ? this.getIntState( "kelp_age" ) : 0;
    }
}
