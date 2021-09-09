package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedExposedCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedExposedCutCopperSlab extends BlockSlab {

    public BlockWaxedExposedCutCopperSlab() {
        super( "minecraft:waxed_exposed_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWaxedExposedCutCopperSlab ) {
                BlockWaxedExposedCutCopperSlab blockSlab = (BlockWaxedExposedCutCopperSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockWaxedExposedDoubleCutCopperSlab() );
                    return true;
                }
            } else if ( block instanceof BlockWaxedExposedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockWaxedExposedDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWaxedExposedCutCopperSlab ) {
                BlockWaxedExposedCutCopperSlab blockSlab = (BlockWaxedExposedCutCopperSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockWaxedExposedDoubleCutCopperSlab());
                    return true;
                }
            } else if ( block instanceof BlockWaxedExposedCutCopperSlab ) {
                world.setBlock( placePosition,  new BlockWaxedExposedDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockWaxedExposedCutCopperSlab ) {
                world.setBlock( placePosition,new BlockWaxedExposedDoubleCutCopperSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemWaxedExposedCutCopperSlab toItem() {
        return new ItemWaxedExposedCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_EXPOSED_CUT_COPPER_SLAB;
    }
}
