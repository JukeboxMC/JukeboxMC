package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class FakeInventory extends ContainerInventory {

    protected String customName = null;

    protected final Map<Player, FakeInventory> openInventory = new ConcurrentHashMap<>();
    protected final Map<Player, List<Vector>> blockPositions = new HashMap<>();

    public FakeInventory( InventoryHolder holder, int holderId, int size ) {
        super( holder, holderId, size );
    }

    @Override
    public void addViewer( Player player, Vector position, byte windowId ) {
        this.viewer.add( player );

        if ( this.openInventory.put( player, this ) != null ){
            return;
        }

        List<Vector> blockPosition = this.onOpenChest( player );
        this.blockPositions.put( player, blockPosition );

        if ( !blockPosition.isEmpty() ) {
            Vector vector = blockPosition.get( 0 );

            ContainerOpenPacket containerOpenPacket = new ContainerOpenPacket();
            containerOpenPacket.setId( windowId );
            containerOpenPacket.setUniqueEntityId( this.holderId );
            containerOpenPacket.setType( this.getWindowTypeId() );
            containerOpenPacket.setBlockPosition( vector.toVector3i() );
            player.getPlayerConnection().sendPacket( containerOpenPacket );

            this.sendContents( player );
        }

    }

    @Override
    public void removeViewer( Player player ) {
        super.removeViewer( player );
        List<Vector> vectors = this.blockPositions.get( player );
        Server.getInstance().getScheduler().scheduleDelayed( () -> {
            for ( Vector position : vectors ) {
                UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
                updateBlockPacket.setBlockPosition( position.toVector3i() );
                updateBlockPacket.setDataLayer( 0 );
                updateBlockPacket.setRuntimeId( player.getWorld().getBlock( position ).getRuntimeId() );
                updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
                player.getPlayerConnection().sendPacket( updateBlockPacket );
            }
        }, 2 );

        this.openInventory.remove( player );
    }

    public abstract List<Vector> onOpenChest( Player player );

    public void setCustomName( String customName ) {
        this.customName = customName;
    }

    public String getCustomName() {
        return this.customName;
    }
}
