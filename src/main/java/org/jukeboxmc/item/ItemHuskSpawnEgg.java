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
public class ItemHuskSpawnEgg extends Item {

    public ItemHuskSpawnEgg() {
        super( "minecraft:husk_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityHusk entityHusk = new EntityHusk();
        entityHusk.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityHusk.getEyeHeight(), 0.5f ) );
        entityHusk.spawn();

        return true;
    }
}