package org.jukeboxmc.block;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemTurtleEgg;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

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
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block clickedBlock = world.getBlock( blockPosition );
        if ( clickedBlock instanceof BlockTurtleEgg blockTurtleEgg ) {
            if ( blockTurtleEgg.getIdentifier().equals( itemIndHand.getIdentifier() ) ) {
                TurtleEggCount turtleEggCount = blockTurtleEgg.getTurtleEggCount();
                if ( turtleEggCount.equals( TurtleEggCount.ONE_EGG ) ) {
                    this.setTurtleEggCount( TurtleEggCount.TWO_EGG );
                    world.setBlock( blockPosition, this, 0 );
                    return true;
                } else if ( turtleEggCount.equals( TurtleEggCount.TWO_EGG ) ) {
                    this.setTurtleEggCount( TurtleEggCount.THREE_EGG );
                    world.setBlock( blockPosition, this , 0);
                    return true;
                } else if ( turtleEggCount.equals( TurtleEggCount.THREE_EGG ) ) {
                    this.setTurtleEggCount( TurtleEggCount.FOUR_EGG );
                    world.setBlock( blockPosition, this, 0 );
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        return true;
    }

    @Override
    public void playBlockBreakSound() {
        this.world.playSound( this.location, SoundEvent.BLOCK_TURTLE_EGG_CRACK );
    }

    @Override
    public BlockType getType() {
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
