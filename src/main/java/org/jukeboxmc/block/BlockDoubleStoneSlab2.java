package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab2Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab2;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleStoneSlab2 extends BlockSlab {

    public BlockDoubleStoneSlab2() {
        super( "minecraft:double_stone_slab2" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemStoneSlab2 toItem() {
        return new ItemStoneSlab2( Block.STATES.get( "minecraft:stone_slab2" ).get( this.blockStates ) );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_SLAB2;
    }


    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return super.getDrops( itemInHand, 2 );
    }

    public Block setStoneSlabType( StoneSlab2Type stoneSlabType ) {
        this.setState( "stone_slab_type_2", stoneSlabType.name().toLowerCase() );
        return this;
    }

    public StoneSlab2Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_2" ) ? StoneSlab2Type.valueOf( this.getStringState( "stone_slab_type_2" ) ) : StoneSlab2Type.RED_SANDSTONE;
    }
}
