package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlabType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleStoneSlab extends BlockSlab {

    public BlockDoubleStoneSlab() {
        super( "minecraft:double_stone_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemStoneSlab toItem() {
        return new ItemStoneSlab( Block.STATES.get( "minecraft:stone_slab" ).get( this.blockStates ) );
    }

    @Override
    public BlockType getType() {
        return BlockType.STONE_SLAB;
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

    public BlockDoubleStoneSlab setStoneSlabType( StoneSlabType stoneSlabType ) {
        return this.setState( "stone_slab_type", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type" ) ) : StoneSlabType.SMOOTH_STONE;
    }
}
