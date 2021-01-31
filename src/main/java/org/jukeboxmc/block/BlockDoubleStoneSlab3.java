package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab3Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleStoneSlab3 extends BlockSlab {

    public BlockDoubleStoneSlab3() {
        super( "minecraft:double_stone_slab3" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlab3Type.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    public Block setStoneSlabType( StoneSlab3Type stoneSlabType ) {
        this.setState( "stone_slab_type_3", stoneSlabType.name().toLowerCase() );
        return this;
    }

    public StoneSlab3Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_3" ) ? StoneSlab3Type.valueOf( this.getStringState( "stone_slab_type_3" ).toUpperCase() ) : StoneSlab3Type.END_STONE_BRICK;
    }
}
