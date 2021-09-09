package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlabType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab extends BlockSlab {

    public BlockStoneSlab() {
        super( "minecraft:stone_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) targetBlock;
                if ( blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) targetBlock;
                if ( !blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else {
            if ( block instanceof BlockStoneSlab ) {
                BlockStoneSlab blockSlab = (BlockStoneSlab) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( placePosition, new BlockDoubleStoneSlab().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        world.setBlock( placePosition, block, 1 );
        return true;
    }

    @Override
    public ItemStoneSlab toItem() {
        return new ItemStoneSlab( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_SLAB;
    }

    public BlockStoneSlab setStoneSlabType( StoneSlabType stoneSlabType ) {
       return this.setState( "stone_slab_type", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type" ) ) : StoneSlabType.SMOOTH_STONE;
    }

}
