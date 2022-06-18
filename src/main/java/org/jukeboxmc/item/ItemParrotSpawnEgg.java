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
public class ItemParrotSpawnEgg extends Item {

    public ItemParrotSpawnEgg() {
        super( "minecraft:parrot_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityParrot entityParrot = new EntityParrot();
        entityParrot.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityParrot.getEyeHeight(), 0.5f ) );
        entityParrot.spawn();

        return true;
    }
}