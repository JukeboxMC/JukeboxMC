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
public class ItemCreeperSpawnEgg extends Item {

    public ItemCreeperSpawnEgg() {
        super( "minecraft:creeper_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityCreeper entityCreeper = new EntityCreeper();
        entityCreeper.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityCreeper.getEyeHeight(), 0.5f ) );
        entityCreeper.spawn();

        return true;
    }
}