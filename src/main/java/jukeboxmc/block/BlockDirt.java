package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDirt;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDirt extends Block {

    public BlockDirt() {
        super( "minecraft:dirt" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirtType( DirtType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemDirt toItem() {
        return new ItemDirt();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DIRT;
    }

    public BlockDirt setDirtType( DirtType dirtType ) {
        return this.setState( "dirt_type", dirtType.name().toLowerCase() );
    }

    public DirtType getDirtType() {
        return this.stateExists( "dirt_type" ) ? DirtType.valueOf( this.getStringState( "dirt_type" ) ) : DirtType.NORMAL;
    }

    public enum DirtType {
        NORMAL,
        COARSE
    }

}
