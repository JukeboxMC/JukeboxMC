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
public class BlockSeagrass extends Block {

    public BlockSeagrass() {
        super( "minecraft:seagrass" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setSeaGrassType( SeaGrassType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSeaGrassType().ordinal() );
    }

    public void setSeaGrassType( SeaGrassType seaGrassType ) {
        this.setState( "sea_grass_type",seaGrassType.name().toLowerCase() );
    }

    public SeaGrassType getSeaGrassType() {
        return this.stateExists( "sea_grass_type" ) ? SeaGrassType.valueOf( this.getStringState( "sea_grass_type" ).toUpperCase() ) : SeaGrassType.DEFAULT;
    }

    public enum SeaGrassType {
        DEFAULT,
        DOUBLE_TOP,
        DOUBLE_BOT
    }
}
