package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTurtleEgg;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTurtleEgg extends BlockWaterlogable {

    public BlockTurtleEgg() {
        super( "minecraft:turtle_egg" );
    }

    @Override
    public ItemTurtleEgg toItem() {
        return new ItemTurtleEgg();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TURTLE_EGG;
    }

    public void setCrackedState( CrackedState crackedState ) {
        this.setState( "cracked_state", crackedState.name().toLowerCase() );
    }

    public CrackedState getCrackedState() {
        return this.stateExists( "cracked_state" ) ? CrackedState.valueOf( this.getStringState( "cracked_state" ) ) : CrackedState.NO_CRACKS;
    }

    public void setTurtleEggCount( TurtleEggCount turtleEggCount ) {
        this.setState( "turtle_egg_count", turtleEggCount.name().toLowerCase() );
    }

    public TurtleEggCount getTurtleEggCount() {
        return this.stateExists( "turtle_egg_count" ) ? TurtleEggCount.valueOf( this.getStringState( "turtle_egg_count" ) ) : TurtleEggCount.ONE_EGG;
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
