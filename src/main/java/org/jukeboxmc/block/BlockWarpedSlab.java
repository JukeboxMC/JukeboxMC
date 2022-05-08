package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWarpedSlab;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockWarpedSlab extends BlockSlab {

    public BlockWarpedSlab() {
        super("minecraft:warped_slab");
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWarpedSlab ) {
                BlockWarpedSlab blockSlab = (BlockWarpedSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleWarpedSlab() );
                    return true;
                }
            } else if ( block instanceof BlockWarpedSlab ) {
                world.setBlock( placePosition, new BlockDoubleWarpedSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWarpedSlab ) {
                BlockWarpedSlab blockSlab = (BlockWarpedSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDoubleWarpedSlab());
                    return true;
                }
            } else if ( block instanceof BlockWarpedSlab ) {
                world.setBlock( placePosition,  new BlockDoubleWarpedSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockWarpedSlab ) {
                world.setBlock( placePosition,new BlockDoubleWarpedSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemWarpedSlab toItem() {
        return new ItemWarpedSlab();
    }

    @Override
    public BlockType getType() {
        return BlockType.WARPED_SLAB;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}