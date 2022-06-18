package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.passive.*;
import org.jukeboxmc.entity.hostile.*;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemBlazeSpawnEgg extends Item {

    public ItemBlazeSpawnEgg() {
        super( "minecraft:blaze_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityBlaze entityBlaze = new EntityBlaze();
        entityBlaze.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityBlaze.getEyeHeight(), 0.5f ) );
        entityBlaze.spawn();

        return true;
    }
}