package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPolishedBlackstoneBrickSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockPolishedBlackstoneBrickSlab extends BlockSlab {

    public BlockPolishedBlackstoneBrickSlab() {
        super( "minecraft:polished_blackstone_brick_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockPolishedBlackstoneBrickSlab ) {
                BlockPolishedBlackstoneBrickSlab blockSlab = (BlockPolishedBlackstoneBrickSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockPolishedBlackstoneBrickDoubleSlab() );
                    return true;
                }
            } else if ( block instanceof BlockPolishedBlackstoneBrickSlab ) {
                world.setBlock( placePosition, new BlockPolishedBlackstoneBrickDoubleSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockPolishedBlackstoneBrickSlab ) {
                BlockPolishedBlackstoneBrickSlab blockSlab = (BlockPolishedBlackstoneBrickSlab) targetBlock;
                if ( !blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockPolishedBlackstoneBrickDoubleSlab() );
                    return true;
                }
            } else if ( block instanceof BlockPolishedBlackstoneBrickSlab ) {
                world.setBlock( placePosition, new BlockPolishedBlackstoneBrickDoubleSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockPolishedBlackstoneBrickSlab ) {
                world.setBlock( placePosition, new BlockPolishedBlackstoneBrickDoubleSlab() );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemPolishedBlackstoneBrickSlab toItem() {
        return new ItemPolishedBlackstoneBrickSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_BLACKSTONE_BRICK_SLAB;
    }

}