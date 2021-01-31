package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab4Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleStoneSlab4 extends BlockSlab {

    public BlockDoubleStoneSlab4() {
        super( "minecraft:double_stone_slab4" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlab4Type.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getStoneSlabType().ordinal() );
    }

    public Block setStoneSlabType( StoneSlab4Type stoneSlabType ) {
        this.setState( "stone_slab_type_4", stoneSlabType.name().toLowerCase() );
        return this;
    }

    public StoneSlab4Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_4" ) ? StoneSlab4Type.valueOf( this.getStringState( "stone_slab_type_4" ).toUpperCase() ) : StoneSlab4Type.MOSSY_STONE_BRICK;
    }

}
