package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.passive.EntityWolf;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemWolfSpawnEgg extends Item {

    public ItemWolfSpawnEgg() {
        super( "minecraft:wolf_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityWolf entityWolf = new EntityWolf();
        entityWolf.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityWolf.getEyeHeight(), 0.5f ) );
        entityWolf.spawn();

        return true;
    }
}