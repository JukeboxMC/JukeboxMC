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
public class ItemZoglinSpawnEgg extends Item {

    public ItemZoglinSpawnEgg() {
        super( "minecraft:zoglin_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityZoglin entityZoglin = new EntityZoglin();
        entityZoglin.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityZoglin.getEyeHeight(), 0.5f ) );
        entityZoglin.spawn();

        return true;
    }
}