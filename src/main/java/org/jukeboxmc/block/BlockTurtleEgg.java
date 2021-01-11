package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTurtleEgg extends Block {

    public BlockTurtleEgg() {
        super( "minecraft:turtle_egg" );
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
