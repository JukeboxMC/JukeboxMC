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
public class BlockSponge extends Block {

    public BlockSponge() {
        super( "minecraft:sponge" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setSpongeType( SpongeType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSpongeType().ordinal() );
    }

    public void setSpongeType( SpongeType spongeType ) {
        this.setState( "sponge_type", spongeType.name().toLowerCase() );
    }

    public SpongeType getSpongeType() {
        return this.stateExists( "sponge_type" ) ? SpongeType.valueOf( this.getStringState( "sponge_type" ).toUpperCase() ) : SpongeType.DRY;
    }

    public enum SpongeType {
        DRY,
        WET
    }
}
