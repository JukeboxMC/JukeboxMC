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
public class ItemEndermanSpawnEgg extends Item {

    public ItemEndermanSpawnEgg() {
        super( "minecraft:enderman_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityEnderman entityEnderman = new EntityEnderman();
        entityEnderman.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityEnderman.getEyeHeight(), 0.5f ) );
        entityEnderman.spawn();

        return true;
    }
}