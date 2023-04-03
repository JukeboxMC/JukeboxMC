package org.jukeboxmc.block.behavior;

import org.apache.commons.math3.util.FastMath;
import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityBanner;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStandingBanner extends Block {
    
    public BlockStandingBanner( Identifier identifier ) {
        super( identifier );
    }

    public BlockStandingBanner( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition );
        if ( blockFace == BlockFace.UP ) {
            this.setSignDirection( SignDirection.values()[(int) FastMath.floor( ( ( player.getLocation().getYaw() + 180 ) * 16 / 360 ) + 0.5 ) & 0x0f] );
            world.setBlock( placePosition, this, 0 );
        } else {
            BlockWallBanner blockWallBanner = Block.create( BlockType.WALL_BANNER );
            blockWallBanner.setBlockFace( blockFace );
            world.setBlock( placePosition, blockWallBanner, 0 );
        }
        int type = itemInHand.getNbt() != null ? itemInHand.getNbt().getInt( "Type", 0 ) : 0;
        BlockEntity.<BlockEntityBanner>create( BlockEntityType.BANNER, block ).setColor( BlockColor.values()[itemInHand.getMeta()] ).setType( type );
        return true;
    }

    @Override
    public BlockEntityBanner getBlockEntity() {
        return (BlockEntityBanner) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }
}
