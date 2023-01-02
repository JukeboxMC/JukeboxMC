package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHangingRoots extends Block {

    public BlockHangingRoots( Identifier identifier ) {
        super( identifier );
    }

    public BlockHangingRoots( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition ).getSide( BlockFace.UP );
        if ( block.isSolid() && !block.getType().equals( BlockType.HANGING_ROOTS )) {
            world.setBlock( placePosition, this );
            return true;
        }
        return false;
    }
}
