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
public class BlockRedFlower extends Block {

    public BlockRedFlower() {
        super( "minecraft:red_flower" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setFlowerType( FlowerType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getFlowerType().ordinal() );
    }

    public void setFlowerType( FlowerType flowerType ) {
        this.setState( "flower_type", flowerType.name().toLowerCase() );
    }

    public FlowerType getFlowerType() {
        return this.stateExists( "flower_type" ) ? FlowerType.valueOf( this.getStringState( "flower_type" ).toUpperCase() ) : FlowerType.TULIP_RED;
    }

    public enum FlowerType {
        POPPY,
        ORCHID,
        ALLIUM,
        HOUSTONIA,
        TULIP_RED,
        TULIP_ORANGE,
        TULIP_WHITE,
        TULIP_PINK,
        OXEYE,
        CORNFLOWER,
        LILY_OF_THE_VALLEY
    }
}
