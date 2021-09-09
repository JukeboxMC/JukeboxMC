package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemOxidizedCutCopperSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOxidizedDoubleCutCopperSlab extends BlockSlab {

    public BlockOxidizedDoubleCutCopperSlab() {
        super( "minecraft:oxidized_double_cut_copper_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemOxidizedCutCopperSlab toItem() {
        return new ItemOxidizedCutCopperSlab();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OXIDIZED_CUT_COPPER_SLAB;
    }
}
