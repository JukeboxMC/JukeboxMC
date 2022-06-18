package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.NpcRequestType;
import com.nukkitx.protocol.bedrock.packet.NpcRequestPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.form.NpcDialogueForm;
import org.jukeboxmc.form.element.NpcDialogueButton;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
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

        if ( form.dialogueButtons().size() == 0 ) {
            if ( packet.getRequestType().equals( NpcRequestType.EXECUTE_CLOSING_COMMANDS ) ) {
                player.removeNpcDialogueForm( form );
            }

            return;
        }

        NpcDialogueButton button = form.dialogueButtons().get( packet.getActionType() );

        if ( button == null ) {
            return;
        }

        if ( ( button.mode().equals( NpcDialogueButton.ButtonMode.ON_ENTER ) && packet.getRequestType().equals( NpcRequestType.EXECUTE_OPENING_COMMANDS ) ) ||
                ( button.mode().equals( NpcDialogueButton.ButtonMode.BUTTON_MODE ) && packet.getRequestType().equals( NpcRequestType.EXECUTE_COMMAND_ACTION ) ) ||
                ( button.mode().equals( NpcDialogueButton.ButtonMode.ON_EXIT ) && packet.getRequestType().equals( NpcRequestType.EXECUTE_CLOSING_COMMANDS ) ) ) {
            button.click().run();

            form.close( player );
        }
    }
}