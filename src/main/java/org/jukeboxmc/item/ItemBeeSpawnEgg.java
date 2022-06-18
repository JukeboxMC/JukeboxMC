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
public class ItemBeeSpawnEgg extends Item {

    public ItemBeeSpawnEgg() {
        super( "minecraft:bee_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityBee entityBee = new EntityBee();
        entityBee.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityBee.getEyeHeight(), 0.5f ) );
        entityBee.spawn();

        return true;
    }
}