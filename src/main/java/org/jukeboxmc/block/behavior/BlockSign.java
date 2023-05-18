package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSign extends Block implements Waterlogable{

    public BlockSign( Identifier identifier ) {
        super( identifier );
    }

    public BlockSign( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        return true;
    }

    @Override
    public BlockEntitySign getBlockEntity() {
        return (BlockEntitySign) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    public List<String> getLines() {
        BlockEntitySign blockEntity = this.getBlockEntity();
        return blockEntity != null ? new ArrayList<>( blockEntity.getLines() ) : null;
    }

    public void setLine( int line, String value ) {
        if ( line > 4 || line < 0 ) {
            return;
        }

        BlockEntitySign blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.getLines().set( line, value );
            blockEntity.updateBlockEntitySign();
        }
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }
}
