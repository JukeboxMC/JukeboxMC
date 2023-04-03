package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
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
public class BlockLantern extends Block {

    public BlockLantern( Identifier identifier ) {
        super( identifier );
    }

    public BlockLantern( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        boolean hanging = blockFace != BlockFace.UP && this.isBlockAboveValid() && ( this.isBlockUnderValid() || blockFace == BlockFace.DOWN );
        if ( this.isBlockUnderValid() && !hanging ) {
            return false;
        }

        this.setHanging( hanging );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
    }

    private boolean isBlockAboveValid() {
        Block up = this.getSide( BlockFace.UP );
        if ( up instanceof BlockLeaves ) {
            return false;
        } else if ( up instanceof BlockFence || up instanceof BlockWall ) {
            return true;
        } else if ( up instanceof BlockSlab ) {
            return !( (BlockSlab) up ).isTopSlot();
        } else if ( up instanceof BlockStairs ) {
            return !( (BlockStairs) up ).isUpsideDown();
        } else return up.isSolid();
    }

    private boolean isBlockUnderValid() {
        Block down = this.getSide( BlockFace.DOWN );
        if ( down instanceof BlockLeaves ) {
            return true;
        } else if ( down instanceof BlockFence || down instanceof BlockWall ) {
            return false;
        } else if ( down instanceof BlockSlab ) {
            return !( (BlockSlab) down ).isTopSlot();
        } else if ( down instanceof BlockStairs ) {
            return !( (BlockStairs) down ).isUpsideDown();
        } else return !down.isSolid();
    }

    public void setHanging( boolean value ) {
        this.setState( "hanging", value ? 1 : 0 );
    }

    public boolean isHanging() {
        return this.stateExists( "hanging" ) && this.getByteState( "hanging" ) == 1;
    }
}
