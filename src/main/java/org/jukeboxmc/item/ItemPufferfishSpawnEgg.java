package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.passive.*;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemPufferfishSpawnEgg extends Item {

    public ItemPufferfishSpawnEgg() {
        super( "minecraft:pufferfish_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityPufferfish entityPufferfish = new EntityPufferfish();
        entityPufferfish.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityPufferfish.getEyeHeight(), 0.5f ) );
        entityPufferfish.spawn();

        return true;
    }
}