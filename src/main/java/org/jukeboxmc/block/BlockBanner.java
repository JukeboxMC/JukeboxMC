package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntityBanner;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBanner extends Block {

    public BlockBanner( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( blockFace == BlockFace.UP ) {
            this.setSignDirection( SignDirection.values()[(int) Math.floor( ( ( player.getLocation().getYaw() + 180 ) * 16 / 360 ) + 0.5 ) & 0x0f] );
            world.setBlock( placePosition, this );
        } else {
            BlockWallBanner blockWallBanner = new BlockWallBanner();
            blockWallBanner.setBlockFace( blockFace );
            world.setBlock( placePosition, blockWallBanner );
        }
        int type = itemIndHand.getNBT() != null ? itemIndHand.getNBT().getInt( "Type", 0 ) : 0;

        BlockEntityType.BANNER.<BlockEntityBanner>createBlockEntity( this ).setColor( BlockColor.values()[itemIndHand.getMeta()] ).setType( type ).spawn();
        return true;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityBanner getBlockEntity() {
        return (BlockEntityBanner) this.world.getBlockEntity( this.getBlockPosition() );
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }
}
