package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedCutCopperSlab extends BlockSlab {

    public BlockWaxedCutCopperSlab() {
        super( "minecraft:waxed_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWaxedCutCopperSlab ) {
                BlockWaxedCutCopperSlab blockSlab = (BlockWaxedCutCopperSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockWaxedDoubleCutCopperSlab() );
                    return true;
                }
            } else if ( block instanceof BlockWaxedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockWaxedDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWaxedCutCopperSlab ) {
                BlockWaxedCutCopperSlab blockSlab = (BlockWaxedCutCopperSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockWaxedDoubleCutCopperSlab());
                    return true;
                }
            } else if ( block instanceof BlockWaxedCutCopperSlab ) {
                world.setBlock( placePosition,  new BlockWaxedDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockWaxedCutCopperSlab ) {
                world.setBlock( placePosition,new BlockWaxedDoubleCutCopperSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemWaxedCutCopperSlab toItem() {
        return new ItemWaxedCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_CUT_COPPER_SLAB;
    }
}
