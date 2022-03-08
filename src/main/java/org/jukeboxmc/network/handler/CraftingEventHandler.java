package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.inventory.CraftingInputInventory;
import org.jukeboxmc.inventory.CursorInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.network.packet.CraftingEventPacket;
import org.jukeboxmc.player.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CraftingEventHandler implements PacketHandler<CraftingEventPacket> {

    @Override
    public void handle( CraftingEventPacket packet, Server server, Player player ) {
        /*
        CursorInventory inventory = player.getCursorInventory();
        for ( int slot = 0; slot < inventory.getSize(); slot++ ) {
            Item content = inventory.getContents()[slot];
            if ( !(content instanceof ItemAir) ) {
                Item item = content.decreaseAmount();

                inventory.setItem( slot, item );
                inventory.sendContents( slot, player );
            }
        }

        player.getInventory().sendContents( player );
        inventory.setItem( packet.getInput().get( 0 ) );
        inventory.sendContents( player );
         */
    }
}
