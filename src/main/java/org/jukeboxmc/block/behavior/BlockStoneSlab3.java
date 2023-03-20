package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.StoneSlab3Type;
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
public class BlockStoneSlab3 extends BlockSlab {

    public BlockStoneSlab3( Identifier identifier ) {
        super( identifier );
    }

    public BlockStoneSlab3( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, @NotNull Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) targetBlock ).isTopSlot() && ( (BlockStoneSlab3) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, Block.<BlockDoubleStoneSlab3>create( BlockType.DOUBLE_STONE_BLOCK_SLAB3 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab3>create( BlockType.DOUBLE_STONE_BLOCK_SLAB3 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab3 && !( (BlockStoneSlab3) targetBlock ).isTopSlot() && ( (BlockStoneSlab3) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, Block.<BlockDoubleStoneSlab3>create( BlockType.DOUBLE_STONE_BLOCK_SLAB3 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab3>create( BlockType.DOUBLE_STONE_BLOCK_SLAB3 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockStoneSlab3 && ( (BlockStoneSlab3) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab3>create( BlockType.DOUBLE_STONE_BLOCK_SLAB3 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    public BlockStoneSlab3 setStoneSlabType(@NotNull StoneSlab3Type stoneSlabType ) {
        return this.setState( "stone_slab_type_3", stoneSlabType.name().toLowerCase() );
    }

    public @NotNull StoneSlab3Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_3" ) ? StoneSlab3Type.valueOf( this.getStringState( "stone_slab_type_3" ) ) : StoneSlab3Type.END_STONE_BRICK;
    }
}
