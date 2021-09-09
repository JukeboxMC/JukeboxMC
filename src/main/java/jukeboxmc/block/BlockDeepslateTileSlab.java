package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateTileSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateTileSlab extends BlockSlab {

    public BlockDeepslateTileSlab() {
        super( "minecraft:deepslate_tile_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockDeepslateTileSlab ) {
                BlockDeepslateTileSlab blockSlab = (BlockDeepslateTileSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDeepslateTileDoubleSlab() );
                    return true;
                }
            } else if ( block instanceof BlockDeepslateTileSlab ) {
                world.setBlock( placePosition, new BlockDeepslateTileDoubleSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockDeepslateTileSlab ) {
                BlockDeepslateTileSlab blockSlab = (BlockDeepslateTileSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDeepslateTileDoubleSlab());
                    return true;
                }
            } else if ( block instanceof BlockDeepslateTileSlab ) {
                world.setBlock( placePosition,  new BlockDeepslateTileDoubleSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockDeepslateTileSlab ) {
                world.setBlock( placePosition,new BlockDeepslateTileDoubleSlab()  );
                return true;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemDeepslateTileSlab toItem() {
        return new ItemDeepslateTileSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_TILE_SLAB;
    }
}
