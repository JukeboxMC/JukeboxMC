package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCutCopperSlab extends BlockSlab {

    public BlockCutCopperSlab() {
        super( "minecraft:cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockCutCopperSlab ) {
                BlockCutCopperSlab blockSlab = (BlockCutCopperSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockWaxedDoubleCutCopperSlab() );
                    return true;
                }
            } else if ( block instanceof BlockCutCopperSlab ) {
                world.setBlock( placePosition, new BlockWaxedDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockCutCopperSlab ) {
                BlockCutCopperSlab blockSlab = (BlockCutCopperSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockWaxedDoubleCutCopperSlab());
                    return true;
                }
            } else if ( block instanceof BlockCutCopperSlab ) {
                world.setBlock( placePosition,  new BlockWaxedDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockCutCopperSlab ) {
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
    public ItemCutCopperSlab toItem() {
        return new ItemCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CUT_COPPER_SLAB;
    }
}
