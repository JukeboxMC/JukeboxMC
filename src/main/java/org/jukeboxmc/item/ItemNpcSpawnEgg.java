package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.passive.EntityNPC;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemNpcSpawnEgg extends Item {

    public ItemNpcSpawnEgg() {
        super( "minecraft:npc_spawn_egg" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        EntityNPC entityNpc = new EntityNPC();
        entityNpc.setLocation( clickedBlock.getSide( blockFace ).getLocation().add( 0.5f, -entityNpc.getEyeHeight(), 0.5f ) );
        entityNpc.spawn();

        return true;
    }
}