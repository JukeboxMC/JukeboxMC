package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedOxidizedCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedOxidizedCutCopperSlab extends BlockSlab {

    public BlockWaxedOxidizedCutCopperSlab() {
        super( "minecraft:waxed_oxidized_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockWaxedOxidizedCutCopperSlab ) {
                BlockWaxedOxidizedCutCopperSlab blockSlab = (BlockWaxedOxidizedCutCopperSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockWaxedOxidizedDoubleCutCopperSlab() );
                    return true;
                }
            } else if ( block instanceof BlockWaxedOxidizedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockWaxedOxidizedDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockWaxedOxidizedCutCopperSlab ) {
                BlockWaxedOxidizedCutCopperSlab blockSlab = (BlockWaxedOxidizedCutCopperSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockWaxedOxidizedDoubleCutCopperSlab());
                    return true;
                }
            } else if ( block instanceof BlockWaxedOxidizedCutCopperSlab ) {
                world.setBlock( placePosition,  new BlockWaxedOxidizedDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockWaxedOxidizedCutCopperSlab ) {
                world.setBlock( placePosition,new BlockWaxedOxidizedDoubleCutCopperSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemWaxedOxidizedCutCopperSlab toItem() {
        return new ItemWaxedOxidizedCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_OXIDIZED_CUT_COPPER_SLAB;
    }
}
