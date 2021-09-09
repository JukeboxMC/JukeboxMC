package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemPolishedDeepslateSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedDeepslateSlab extends BlockSlab {

    public BlockPolishedDeepslateSlab() {
        super( "minecraft:polished_deepslate_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockPolishedDeepslateSlab ) {
                BlockPolishedDeepslateSlab blockSlab = (BlockPolishedDeepslateSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockPolishedDeepslateDoubleSlab() );
                    return true;
                }
            } else if ( block instanceof BlockPolishedDeepslateSlab ) {
                world.setBlock( placePosition, new BlockPolishedDeepslateDoubleSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockPolishedDeepslateSlab ) {
                BlockPolishedDeepslateSlab blockSlab = (BlockPolishedDeepslateSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockPolishedDeepslateDoubleSlab());
                    return true;
                }
            } else if ( block instanceof BlockPolishedDeepslateSlab ) {
                world.setBlock( placePosition,  new BlockPolishedDeepslateDoubleSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockPolishedDeepslateSlab ) {
                world.setBlock( placePosition,new BlockPolishedDeepslateDoubleSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemPolishedDeepslateSlab toItem() {
        return new ItemPolishedDeepslateSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_DEEPSLATE_SLAB;
    }
}
