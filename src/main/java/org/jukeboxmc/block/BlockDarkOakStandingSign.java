package org.jukeboxmc.block;

import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDarkOakStandingSign;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakStandingSign extends BlockSign {

    public BlockDarkOakStandingSign() {
        super( "minecraft:darkoak_standing_sign" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition );
        if ( blockFace == BlockFace.UP ) {
            this.setSignDirection( SignDirection.values()[(int) FastMath.floor( ( ( player.getLocation().getYaw() + 180 ) * 16 / 360 ) + 0.5 ) & 0x0f] );
            world.setBlock( placePosition, this, 0 );
        } else {
            BlockDarkOakWallSign blockWallSign = new BlockDarkOakWallSign();
            blockWallSign.setBlockFace( blockFace );
            world.setBlock( placePosition, blockWallSign, 0 );
        }
        world.setBlock( placePosition, block, 1 );
        BlockEntityType.SIGN.<BlockEntitySign>createBlockEntity( this ).spawn();
        return true;
    }

    @Override
    public ItemDarkOakStandingSign toItem() {
        return new ItemDarkOakStandingSign();
    }

    @Override
    public BlockType getType() {
        return BlockType.DARK_OAK_STANDING_SIGN;
    }

}
