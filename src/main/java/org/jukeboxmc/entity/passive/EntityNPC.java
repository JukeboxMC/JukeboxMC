package org.jukeboxmc.entity.passive;

import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.event.entity.EntityDamageByEntityEvent;
import org.jukeboxmc.event.entity.EntityDamageEvent;
import org.jukeboxmc.form.NpcDialogueForm;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
@Getter
@Setter
@Accessors ( fluent = true )
public class EntityNPC extends EntityLiving {

    private NpcDialogueForm npcDialogueForm;

    public EntityNPC() {
        super();

        this.updateMetadata( this.metadata
                .setString( EntityData.NPC_DATA, "{\"picker_offsets\":{\"scale\":[1.70,1.70,1.70],\"translate\":[0,20,0]},\"portrait_offsets\":{\"scale\":[1.750,1.750,1.750],\"translate\":[-7,50,0]},\"skin_list\":[{\"variant\":0},{\"variant\":1},{\"variant\":2},{\"variant\":3},{\"variant\":4},{\"variant\":5},{\"variant\":6},{\"variant\":7},{\"variant\":8},{\"variant\":9},{\"variant\":10},{\"variant\":11},{\"variant\":12},{\"variant\":13},{\"variant\":14},{\"variant\":15},{\"variant\":16},{\"variant\":17},{\"variant\":18},{\"variant\":19},{\"variant\":25},{\"variant\":26},{\"variant\":27},{\"variant\":28},{\"variant\":29},{\"variant\":30},{\"variant\":31},{\"variant\":32},{\"variant\":33},{\"variant\":34},{\"variant\":20},{\"variant\":21},{\"variant\":22},{\"variant\":23},{\"variant\":24}]}}" )
                .setByte( EntityData.HAS_NPC_COMPONENT, (byte) 0x01 )
                .setString( EntityData.NAMETAG, "NPC" )
                .setByte( EntityData.NAMETAG_ALWAYS_SHOW, (byte) 0x01 )
                .setString( EntityData.INTERACTIVE_TAG, "" )
                .setString( EntityData.URL_TAG, "" )
        );
    }

    @Override
    public String getName() {
        return "NPC";
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.95f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.NPC;
    }

    public void setVariant( byte variant ) {
        if ( variant > 34 || variant < 0 ) {
            throw new IllegalStateException( "variant must be a value between zero and 34" );
        }

        this.updateMetadata( this.metadata.setInt( EntityData.VARIANT, variant ) );
    }

    @Override
    public boolean damage( EntityDamageEvent entityDamageEvent ) {
        if ( entityDamageEvent instanceof EntityDamageByEntityEvent event && event.getDamager() instanceof Player player && player.getGameMode().equals( GameMode.CREATIVE ) ) {
            RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
            removeEntityPacket.setUniqueEntityId( this.entityId );

            Server.getInstance().broadcastPacket( removeEntityPacket );
        }

        return false;
    }

    @Override
    public void interact( Player player, Vector clickVector ) {
        if ( this.npcDialogueForm == null ) {
            this.npcDialogueForm = new NpcDialogueForm()
                    .title( this.getNameTag() )
                    .dialogue( "" )
                    .npc( this );
        }

        this.npcDialogueForm.create( player );
    }
}