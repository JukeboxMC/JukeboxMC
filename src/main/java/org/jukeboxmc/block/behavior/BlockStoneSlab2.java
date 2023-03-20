package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.StoneSlab2Type;
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
public class BlockStoneSlab2 extends BlockSlab{

    public BlockStoneSlab2( Identifier identifier ) {
        super( identifier );
    }

    public BlockStoneSlab2( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, @NotNull Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab2 && ( (BlockStoneSlab2) targetBlock ).isTopSlot() && ( (BlockStoneSlab2) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, Block.<BlockDoubleStoneSlab2>create( BlockType.DOUBLE_STONE_BLOCK_SLAB2 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab2 && ( (BlockStoneSlab2) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab2>create( BlockType.DOUBLE_STONE_BLOCK_SLAB2 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab2 && !( (BlockStoneSlab2) targetBlock ).isTopSlot() && ( (BlockStoneSlab2) targetBlock ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( blockPosition, Block.<BlockDoubleStoneSlab2>create( BlockType.DOUBLE_STONE_BLOCK_SLAB2 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else if ( block instanceof BlockStoneSlab2 && ( (BlockStoneSlab2) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab2>create( BlockType.DOUBLE_STONE_BLOCK_SLAB2 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            }
        } else {
            if ( block instanceof BlockStoneSlab2 && ( (BlockStoneSlab2) block ).getStoneSlabType() == this.getStoneSlabType() ) {
                world.setBlock( placePosition, Block.<BlockDoubleStoneSlab2>create( BlockType.DOUBLE_STONE_BLOCK_SLAB2 ).setStoneSlabType( this.getStoneSlabType() ) );
                return true;
            } else {
                this.setTopSlot( clickedPosition.getY() > 0.5 && !world.getBlock( blockPosition ).canBeReplaced( this ) );
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    public BlockStoneSlab2 setStoneSlabType(@NotNull StoneSlab2Type stoneSlabType ) {
        return this.setState( "stone_slab_type_2", stoneSlabType.name().toLowerCase() );
    }

    public @NotNull StoneSlab2Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_2" ) ? StoneSlab2Type.valueOf( this.getStringState( "stone_slab_type_2" ) ) : StoneSlab2Type.RED_SANDSTONE;
    }
}
