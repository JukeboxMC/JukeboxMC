package org.jukeboxmc.inventory;

import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.BlockEventPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnderChestInventory extends ContainerInventory {

    private Vector position;

    public EnderChestInventory( InventoryHolder holder ) {
        super( holder, -1, 27 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.ENDER_CHEST;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.CONTAINER;
    }

    @Override
    public void onOpen( Player player ) {
        if ( this.viewer.size() == 1 ) {
            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setPosition( this.position );
            blockEventPacket.setData1( 1 );
            blockEventPacket.setData2( 2 );
            for ( Player players : player.getWorld().getPlayers() ) {
                player.getWorld().playSound( players, LevelSound.ENDERCHEST_OPEN );
            }
            player.getWorld().sendChunkPacket( this.position.getBlockX() >> 4, this.position.getBlockZ() >> 4, blockEventPacket );
        }
    }

    @Override
    public void onClose( Player player ) {
        if ( this.viewer.size() == 1 ) {
            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setPosition( this.position );
            blockEventPacket.setData1( 1 );
            blockEventPacket.setData2( 0 );
            for ( Player players : player.getWorld().getPlayers() ) {
                player.getWorld().playSound( players, LevelSound.ENDERCHEST_CLOSED );
            }
            player.getWorld().sendChunkPacket( this.position.getBlockX() >> 4, this.position.getBlockZ() >> 4, blockEventPacket );
        }
    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition( Vector position ) {
        this.position = position;
    }
}
