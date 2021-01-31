package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPrismarine extends Block {

    public BlockPrismarine() {
        super( "minecraft:prismarine" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setPrismarineType( PrismarineType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getPrismarineType().ordinal() );
    }

    public void setPrismarineType( PrismarineType prismarineType ) {
        this.setState( "prismarine_block_type", prismarineType.name().toLowerCase() );
    }

    public PrismarineType getPrismarineType() {
        return this.stateExists( "prismarine_block_type" ) ? PrismarineType.valueOf( this.getStringState( "prismarine_block_type" ).toUpperCase() ) : PrismarineType.DEFAULT;
    }

    public enum PrismarineType {
        DEFAULT,
        DARK,
        BRICKS
    }
}
