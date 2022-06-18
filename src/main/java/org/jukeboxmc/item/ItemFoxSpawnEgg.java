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
public class ItemFoxSpawnEgg extends Item {

    public ItemFoxSpawnEgg() {
        super( "minecraft:fox_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityFox entityFox = new EntityFox();
        entityFox.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityFox.getEyeHeight(), 0.5f ) );
        entityFox.spawn();

        return true;
    }
}