package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWeatheredCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeatheredDoubleCutCopperSlab extends BlockSlab {

    public BlockWeatheredDoubleCutCopperSlab() {
        super( "minecraft:weathered_double_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemWeatheredCutCopperSlab toItem() {
        return new ItemWeatheredCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEATHERED_CUT_COPPER_SLAB;
    }
}
