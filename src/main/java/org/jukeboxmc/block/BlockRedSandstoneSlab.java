package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemRedSandstoneSlab;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedSandstoneSlab extends BlockSlab {

    public BlockRedSandstoneSlab() {
        super( "minecraft:stone_slab2" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlabType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return new ItemRedSandstoneSlab().setMeta( this.getStoneSlabType().ordinal() );
    }

    public void setStoneSlabType( StoneSlabType stoneSlabType ) {
        this.setState( "stone_slab_type_2", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type_2" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type_2" ).toUpperCase() ) : StoneSlabType.RED_SANDSTONE;
    }

    public enum StoneSlabType {
        RED_SANDSTONE,
        PURPUR,
        PRISMARINE_ROUGH,
        PRISMARINE_DARK,
        PRISMARINE_BRICK,
        MOSSY_COBBLESTONE,
        SMOOTH_SANDSTONE,
        RED_NETHER_BRICK
    }
}
