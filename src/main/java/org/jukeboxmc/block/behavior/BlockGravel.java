package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.passiv.EntityFallingBlock;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGravel extends Block {

    public BlockGravel( Identifier identifier ) {
        super( identifier );
    }

    public BlockGravel( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        world.setBlock( placePosition, this, 0 );
        return true;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason.equals( UpdateReason.NORMAL ) ) {
            this.location.getWorld().scheduleBlockUpdate( this.location, 1 );
        } else if ( updateReason.equals( UpdateReason.SCHEDULED ) ) {
            Block blockDown = this.location.getBlock().clone().getSide( BlockFace.DOWN );
            if ( blockDown.getType().equals( BlockType.AIR ) ) {
                this.location.getWorld().setBlock( this.location, Block.create( BlockType.AIR ) );

                EntityFallingBlock entity = Entity.create( EntityType.FALLING_BLOCK );
                entity.setLocation( this.location.add( 0.5f, 0f, 0.5f ) );
                entity.setBlock( this );
                entity.spawn();
            }
        }
        return -1;
    }
}
