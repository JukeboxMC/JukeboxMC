package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemEndStoneBrickSlab;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndStoneBrickSlab extends BlockSlab {

    public BlockEndStoneBrickSlab() {
        super( "minecraft:stone_slab3" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlabType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return new ItemEndStoneBrickSlab().setMeta( this.getStoneSlabType().ordinal() );
    }

    public void setStoneSlabType( StoneSlabType stoneSlabType ) {
        this.setState( "stone_slab_type_3", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type_3" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type_3" ).toUpperCase() ) : StoneSlabType.END_STONE_BRICK;
    }

    public enum StoneSlabType {
        END_STONE_BRICK,
        SMOOTH_RED_SANDSTONE,
        POLISHED_ANDESITE,
        ANDESITE,
        DIORITE,
        POLISHED_DIORITE,
        GRANITE,
        POLISHED_GRANITE
    }
}
