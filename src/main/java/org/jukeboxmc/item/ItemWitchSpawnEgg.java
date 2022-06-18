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
public class ItemWitchSpawnEgg extends Item {

    public ItemWitchSpawnEgg() {
        super( "minecraft:witch_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityWitch entityWitch = new EntityWitch();
        entityWitch.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityWitch.getEyeHeight(), 0.5f ) );
        entityWitch.spawn();

        return true;
    }
}