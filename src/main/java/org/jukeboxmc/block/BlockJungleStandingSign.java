package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleStandingSign extends Block {

    public BlockJungleStandingSign() {
        super( "minecraft:jungle_standing_sign" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Item itemIndHand, BlockFace blockFace ) {
        if ( blockFace == BlockFace.UP ) {
            this.setSignDirection( SignDirection.values()[(int) Math.floor( ( ( player.getLocation().getYaw() + 180 ) * 16 / 360 ) + 0.5 ) & 0x0f] );
            world.setBlock( placePosition, this );
        } else {
            BlockJungleWallSign blockWallSign = new BlockJungleWallSign();
            blockWallSign.setBlockFace( blockFace );
            world.setBlock( placePosition, blockWallSign );
        }
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }
}
