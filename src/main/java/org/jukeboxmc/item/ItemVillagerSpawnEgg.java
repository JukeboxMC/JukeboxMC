package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.passive.EntityVillagerV2;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemVillagerSpawnEgg extends Item {

    public ItemVillagerSpawnEgg() {
        super( "minecraft:villager_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityVillagerV2 entityVillager = new EntityVillagerV2();
        entityVillager.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityVillager.getEyeHeight(), 0.5f ) );
        entityVillager.spawn();

        return true;
    }
}