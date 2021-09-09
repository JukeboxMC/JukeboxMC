package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBlackstoneSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockBlackstoneSlab extends BlockSlab {

    public BlockBlackstoneSlab() {
        super("minecraft:blackstone_slab");
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockBlackstoneSlab ) {
                BlockBlackstoneSlab blockSlab = (BlockBlackstoneSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoubleBlackstoneSlab() );
                    return true;
                }
            } else if ( block instanceof BlockBlackstoneSlab ) {
                world.setBlock( placePosition, new BlockDoubleBlackstoneSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockBlackstoneSlab ) {
                BlockBlackstoneSlab blockSlab = (BlockBlackstoneSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDoubleBlackstoneSlab());
                    return true;
                }
            } else if ( block instanceof BlockBlackstoneSlab ) {
                world.setBlock( placePosition,  new BlockDoubleBlackstoneSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockBlackstoneSlab ) {
                world.setBlock( placePosition,new BlockDoubleBlackstoneSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, block, 1 );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemBlackstoneSlab toItem() {
        return new ItemBlackstoneSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLACKSTONE_SLAB;
    }

}