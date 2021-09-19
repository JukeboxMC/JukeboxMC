package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab4Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab4;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleStoneSlab4 extends BlockSlab {

    public BlockDoubleStoneSlab4() {
        super( "minecraft:double_stone_slab4" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemStoneSlab4 toItem() {
        return new ItemStoneSlab4( Block.STATES.get( "minecraft:stone_slab4" ).get( this.blockStates ) );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_SLAB4;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return super.getDrops( itemInHand, 2 );
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public Block setStoneSlabType( StoneSlab4Type stoneSlabType ) {
        this.setState( "stone_slab_type_4", stoneSlabType.name().toLowerCase() );
        return this;
    }

    public StoneSlab4Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_4" ) ? StoneSlab4Type.valueOf( this.getStringState( "stone_slab_type_4" ) ) : StoneSlab4Type.MOSSY_STONE_BRICK;
    }

}
