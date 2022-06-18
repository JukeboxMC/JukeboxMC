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
public class ItemZombieHorseSpawnEgg extends Item {

    public ItemZombieHorseSpawnEgg() {
        super( "minecraft:zombie_horse_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityZombieHorse entityZombieHorse = new EntityZombieHorse();
        entityZombieHorse.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityZombieHorse.getEyeHeight(), 0.5f ) );
        entityZombieHorse.spawn();

        return true;
    }
}