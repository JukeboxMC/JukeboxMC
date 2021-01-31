package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockWarpedSlab extends BlockSlab {

    public BlockWarpedSlab() {
        super("minecraft:warped_slab");
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWarpedSlab ) {
                BlockWarpedSlab blockSlab = (BlockWarpedSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleWarpedSlab() );
                    return;
                }
            } else if ( block instanceof BlockWarpedSlab ) {
                world.setBlock( placePosition, new BlockDoubleWarpedSlab() );
                return;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWarpedSlab ) {
                BlockWarpedSlab blockSlab = (BlockWarpedSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDoubleWarpedSlab());
                    return;
                }
            } else if ( block instanceof BlockWarpedSlab ) {
                world.setBlock( placePosition,  new BlockDoubleWarpedSlab() );
                return;
            }
        } else {
            if ( block instanceof BlockWarpedSlab ) {
                world.setBlock( placePosition,new BlockDoubleWarpedSlab()  );
                return;
            }
        }
        world.setBlock( placePosition, this );
    }
}