package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemOxidizedCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOxidizedCutCopperSlab extends BlockSlab {

    public BlockOxidizedCutCopperSlab() {
        super( "minecraft:oxidized_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockOxidizedCutCopperSlab ) {
                BlockOxidizedCutCopperSlab blockSlab = (BlockOxidizedCutCopperSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockOxidizedDoubleCutCopperSlab() );
                    return true;
                }
            } else if ( block instanceof BlockOxidizedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockOxidizedCutCopperSlab ) {
                BlockOxidizedCutCopperSlab blockSlab = (BlockOxidizedCutCopperSlab) targetBlock;
                if ( !blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockOxidizedDoubleCutCopperSlab() );
                    return true;
                }
            } else if ( block instanceof BlockOxidizedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockOxidizedCutCopperSlab ) {
                world.setBlock( placePosition, new BlockOxidizedDoubleCutCopperSlab() );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemOxidizedCutCopperSlab toItem() {
        return new ItemOxidizedCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OXIDIZED_CUT_COPPER_SLAB;
    }
}
