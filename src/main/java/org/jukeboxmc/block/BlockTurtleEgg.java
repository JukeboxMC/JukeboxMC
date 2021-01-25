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
public class BlockTurtleEgg extends Block {

    public BlockTurtleEgg() {
        super( "minecraft:turtle_egg" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setCrackedState( CrackedState.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getCrackedState().ordinal() );
    }

    public void setCrackedState( CrackedState crackedState ) {
        this.setState( "cracked_state", crackedState.name().toLowerCase() );
    }

    public CrackedState getCrackedState() {
        return this.stateExists( "cracked_state" ) ? CrackedState.valueOf( this.getStringState( "cracked_state" ).toUpperCase() ) : CrackedState.NO_CRACKS;
    }

    public void setTurtleEggCount( TurtleEggCount turtleEggCount ) {
        this.setState( "turtle_egg_count", turtleEggCount.name().toLowerCase() );
    }

    public TurtleEggCount getTurtleEggCount() {
        return this.stateExists( "turtle_egg_count" ) ? TurtleEggCount.valueOf( this.getStringState( "turtle_egg_count" ).toUpperCase() ) : TurtleEggCount.ONE_EGG;
    }

    public enum CrackedState {
        NO_CRACKS,
        CRACKED,
        MAX_CRACKED
    }

    public enum TurtleEggCount {
        ONE_EGG,
        TWO_EGG,
        THREE_EGG,
        FOUR_EGG
    }
}
