package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAcaciaStandingSign;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaStandingSign extends BlockSign {

    public BlockAcaciaStandingSign() {
        super( "minecraft:acacia_standing_sign" );
    }

    @Override
    public Item toItem() {
        return new ItemAcaciaStandingSign();
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        if ( blockFace == BlockFace.UP ) {
            this.setSignDirection( SignDirection.values()[(int) Math.floor( ( ( player.getLocation().getYaw() + 180 ) * 16 / 360 ) + 0.5 ) & 0x0f] );
            world.setBlock( placePosition, this );
        } else {
            BlockAcaciaWallSign blockWallSign = new BlockAcaciaWallSign();
            blockWallSign.setBlockFace( blockFace );
            world.setBlock( placePosition, blockWallSign );
        }
        BlockEntityType.SIGN.<BlockEntitySign>createBlockEntity( this ).spawn();
        return true;
    }
}
