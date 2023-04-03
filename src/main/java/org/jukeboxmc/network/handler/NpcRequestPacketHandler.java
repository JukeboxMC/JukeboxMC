package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.data.NpcRequestType;
import org.cloudburstmc.protocol.bedrock.packet.NpcRequestPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.form.NpcDialogueForm;
import org.jukeboxmc.form.element.NpcDialogueButton;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class NpcRequestPacketHandler implements PacketHandler<NpcRequestPacket> {

    @Override
    public void handle( NpcRequestPacket packet, Server server, Player player ) {
        NpcDialogueForm form = player.getOpenNpcDialogueForms().stream()
                .filter( npcDialogueForm -> npcDialogueForm.sceneName().equalsIgnoreCase( packet.getSceneName() ) )
                .findAny().orElse( null );

        if ( form == null ) {
            return;
        }

        NpcRequestType requestType = packet.getRequestType();
        if ( form.dialogueButtons().size() == 0 ) {
            if ( requestType.equals( NpcRequestType.EXECUTE_CLOSING_COMMANDS ) ) {
                player.removeNpcDialogueForm( form );
            }

            return;
        }

        NpcDialogueButton button = form.dialogueButtons().get( packet.getActionType() );

        if ( button == null ) {
            return;
        }

        NpcDialogueButton.ButtonMode mode = button.mode();
        if ( ( mode.equals( NpcDialogueButton.ButtonMode.ON_ENTER ) && requestType.equals( NpcRequestType.EXECUTE_OPENING_COMMANDS ) ) ||
                ( mode.equals( NpcDialogueButton.ButtonMode.BUTTON_MODE ) && requestType.equals( NpcRequestType.EXECUTE_COMMAND_ACTION ) ) ||
                ( mode.equals( NpcDialogueButton.ButtonMode.ON_EXIT ) && requestType.equals( NpcRequestType.EXECUTE_CLOSING_COMMANDS ) ) ) {
            button.click().run();

            form.close( player );
        }
    }
}
