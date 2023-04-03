package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.StoneSlabType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab extends BlockSlab {

    public BlockStoneSlab( Identifier identifier ) {
        super( identifier );
    }

    public BlockStoneSlab( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab && ( (BlockStoneSlab) targetBlock ).isTopSlot() && ( (BlockStoneSlab) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, Block.<BlockDoubleStoneSlab>create( BlockType.DOUBLE_STONE_BLOCK_SLAB ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab && ( (BlockStoneSlab) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab>create( BlockType.DOUBLE_STONE_BLOCK_SLAB ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab && !( (BlockStoneSlab) targetBlock ).isTopSlot() && ( (BlockStoneSlab) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, Block.<BlockDoubleStoneSlab>create( BlockType.DOUBLE_STONE_BLOCK_SLAB ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab && ( (BlockStoneSlab) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab>create( BlockType.DOUBLE_STONE_BLOCK_SLAB ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockStoneSlab && ( (BlockStoneSlab) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab>create( BlockType.DOUBLE_STONE_BLOCK_SLAB ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    public BlockStoneSlab setStoneSlabType( StoneSlabType stoneSlabType ) {
        return this.setState( "stone_slab_type", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type" ) ) : StoneSlabType.SMOOTH_STONE;
    }
}
