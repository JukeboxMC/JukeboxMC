package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockBlackstoneSlab extends BlockSlab {

    public BlockBlackstoneSlab() {
        super("minecraft:blackstone_slab");
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockBlackstoneSlab ) {
                BlockBlackstoneSlab blockSlab = (BlockBlackstoneSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleBlackstoneSlab() );
                    return;
                }
            } else if ( block instanceof BlockBlackstoneSlab ) {
                world.setBlock( placePosition, new BlockDoubleBlackstoneSlab() );
                return;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockBlackstoneSlab ) {
                BlockBlackstoneSlab blockSlab = (BlockBlackstoneSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDoubleBlackstoneSlab());
                    return;
                }
            } else if ( block instanceof BlockBlackstoneSlab ) {
                world.setBlock( placePosition,  new BlockDoubleBlackstoneSlab() );
                return;
            }
        } else {
            if ( block instanceof BlockBlackstoneSlab ) {
                world.setBlock( placePosition,new BlockDoubleBlackstoneSlab()  );
                return;
            }
        }
        world.setBlock( placePosition, this );
    }
}