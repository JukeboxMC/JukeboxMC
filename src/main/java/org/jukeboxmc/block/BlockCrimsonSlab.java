package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockCrimsonSlab extends BlockSlab {

    public BlockCrimsonSlab() {
        super("minecraft:crimson_slab");
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockCrimsonSlab ) {
                BlockCrimsonSlab blockSlab = (BlockCrimsonSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleCrimsonSlab() );
                    return;
                }
            } else if ( block instanceof BlockCrimsonSlab ) {
                world.setBlock( placePosition, new BlockDoubleCrimsonSlab() );
                return;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockCrimsonSlab ) {
                BlockCrimsonSlab blockSlab = (BlockCrimsonSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDoubleCrimsonSlab());
                    return;
                }
            } else if ( block instanceof BlockCrimsonSlab ) {
                world.setBlock( placePosition,  new BlockDoubleCrimsonSlab() );
                return;
            }
        } else {
            if ( block instanceof BlockCrimsonSlab ) {
                world.setBlock( placePosition,new BlockDoubleCrimsonSlab()  );
                return;
            }
        }
        world.setBlock( placePosition, this );
    }

}