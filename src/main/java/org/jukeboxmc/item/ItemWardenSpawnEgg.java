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
public class ItemWardenSpawnEgg extends Item {

    public ItemWardenSpawnEgg() {
        super( "minecraft:warden_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityWarden entityWarden = new EntityWarden();
        entityWarden.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityWarden.getEyeHeight(), 0.5f ) );
        entityWarden.spawn();

        return true;
    }
}