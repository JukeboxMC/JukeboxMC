package jukeboxmc.event.network;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.event.Event;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PacketReceiveEvent extends Event implements Cancellable {

    private final Player player;
    private Packet packet;

    public PacketReceiveEvent( Player player, Packet packet ) {
        this.player = player;
        this.packet = packet;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket( Packet packet ) {
        this.packet = packet;
    }
}
