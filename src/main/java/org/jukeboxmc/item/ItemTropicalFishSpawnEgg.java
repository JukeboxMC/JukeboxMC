package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.passive.EntityTropicalfish;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemTropicalFishSpawnEgg extends Item {

    public ItemTropicalFishSpawnEgg() {
        super( "minecraft:tropical_fish_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityTropicalfish entityTropicalFish = new EntityTropicalfish();
        entityTropicalFish.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityTropicalFish.getEyeHeight(), 0.5f ) );
        entityTropicalFish.spawn();

        return true;
    }
}