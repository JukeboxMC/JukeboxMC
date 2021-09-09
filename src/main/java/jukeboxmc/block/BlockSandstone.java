package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSandstone;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSandstone extends Block {

    public BlockSandstone() {
        super( "minecraft:sandstone" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setSandStoneType( SandStoneType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemSandstone toItem() {
        return new ItemSandstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SANDSTONE;
    }

    public void setSandStoneType( SandStoneType sandStoneType ) {
        this.setState( "sand_stone_type", sandStoneType.name().toLowerCase() );
    }

    public SandStoneType getSandStoneType() {
        return this.stateExists( "sand_stone_type" ) ? SandStoneType.valueOf( this.getStringState( "sand_stone_type" ) ) : SandStoneType.DEFAULT;
    }

    public enum SandStoneType {
        DEFAULT,
        HEIROGLYPHS,
        CUT,
        SMOOTH
    }
}
