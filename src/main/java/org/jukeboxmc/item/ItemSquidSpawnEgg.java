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
public class ItemSquidSpawnEgg extends Item {

    public ItemSquidSpawnEgg() {
        super( "minecraft:squid_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntitySquid entitySquid = new EntitySquid();
        entitySquid.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entitySquid.getEyeHeight(), 0.5f ) );
        entitySquid.spawn();

        return true;
    }
}