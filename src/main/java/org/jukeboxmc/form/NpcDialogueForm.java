package org.jukeboxmc.form;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.packet.NpcDialoguePacket;
import org.cloudburstmc.protocol.bedrock.packet.SetEntityDataPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.passive.EntityNPC;
import org.jukeboxmc.form.element.NpcDialogueButton;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Kaooot
 * @version 1.0
 */
@Accessors ( fluent = true, chain = true )
public class NpcDialogueForm {

    @Getter
    private final String sceneName = UUID.randomUUID().toString();

    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String dialogue;
    @Getter
    @Setter
    private EntityNPC npc;
    private String actionJson = "";

    @Getter
    private final ObjectArrayList<NpcDialogueButton> dialogueButtons = new ObjectArrayList<>();

    public NpcDialogueForm buttons( NpcDialogueButton... buttons ) {
        String urlTag = this.npc.getMetadata().getString( EntityDataTypes.NPC_DATA );

        List<JsonObject> urlTags = Utils.gson().fromJson( urlTag, List.class ) == null ?
                new ArrayList<>() : Utils.gson().fromJson( urlTag, List.class );

        for ( NpcDialogueButton button : buttons ) {
            boolean found = false;

            for ( JsonObject tag : urlTags ) {
                if ( tag.get( "button_name" ).getAsString().equalsIgnoreCase( button.text() ) ) {
                    found = true;
                }
            }

            if ( !found ) {
                urlTags.add( button.toJsonObject() );
            }
        }

        this.actionJson = Utils.gson().toJson( urlTags );

        this.dialogueButtons.addAll( Arrays.asList( buttons ) );

        return this;
    }

    public void create( Player player ) {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setRuntimeEntityId( this.npc.getEntityId() );
        setEntityDataPacket.getMetadata().putAll( this.npc.getMetadata()
                .setString( EntityDataTypes.NAME, this.title )
                .setString( EntityDataTypes.NPC_DATA, this.dialogue )
                .getEntityDataMap() );
        Server.getInstance().broadcastPacket( setEntityDataPacket );

        NpcDialoguePacket npcDialoguePacket = new NpcDialoguePacket();
        npcDialoguePacket.setUniqueEntityId( this.npc.getEntityId() );
        npcDialoguePacket.setAction( NpcDialoguePacket.Action.OPEN );
        npcDialoguePacket.setDialogue( this.dialogue );
        npcDialoguePacket.setNpcName( this.title );
        npcDialoguePacket.setSceneName( this.sceneName );
        npcDialoguePacket.setActionJson( this.actionJson );

        player.getPlayerConnection().sendPacket( npcDialoguePacket );

        player.addNpcDialogueForm( this );
    }

    public void close( Player player ) {
        NpcDialoguePacket npcDialoguePacket = new NpcDialoguePacket();
        npcDialoguePacket.setUniqueEntityId( this.npc.getEntityId() );
        npcDialoguePacket.setAction( NpcDialoguePacket.Action.CLOSE );
        npcDialoguePacket.setDialogue( this.dialogue );
        npcDialoguePacket.setNpcName( this.title );
        npcDialoguePacket.setSceneName( this.sceneName );
        npcDialoguePacket.setActionJson( this.actionJson );

        player.getPlayerConnection().sendPacket( npcDialoguePacket );

        player.removeNpcDialogueForm( this );
    }
}