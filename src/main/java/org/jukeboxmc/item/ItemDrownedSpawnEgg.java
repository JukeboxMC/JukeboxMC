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
public class ItemDrownedSpawnEgg extends Item {

    public ItemDrownedSpawnEgg() {
        super( "minecraft:drowned_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityDrowned entityDrowned = new EntityDrowned();
        entityDrowned.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityDrowned.getEyeHeight(), 0.5f ) );
        entityDrowned.spawn();

        return true;
    }
}