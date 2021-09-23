package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCrimsonSlab;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockCrimsonSlab extends BlockSlab {

    public BlockCrimsonSlab() {
        super("minecraft:crimson_slab");
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockCrimsonSlab ) {
                BlockCrimsonSlab blockSlab = (BlockCrimsonSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleCrimsonSlab() );
                    return true;
                }
            } else if ( block instanceof BlockCrimsonSlab ) {
                world.setBlock( placePosition, new BlockDoubleCrimsonSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockCrimsonSlab ) {
                BlockCrimsonSlab blockSlab = (BlockCrimsonSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDoubleCrimsonSlab());
                    return true;
                }
            } else if ( block instanceof BlockCrimsonSlab ) {
                world.setBlock( placePosition,  new BlockDoubleCrimsonSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockCrimsonSlab ) {
                world.setBlock( placePosition,new BlockDoubleCrimsonSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemCrimsonSlab toItem() {
        return new ItemCrimsonSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_SLAB;
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