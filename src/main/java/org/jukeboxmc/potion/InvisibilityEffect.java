package org.jukeboxmc.potion;

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InvisibilityEffect extends Effect {

    @Override
    public int getId() {
        return 14;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.INVISIBILITY;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 127, 131, 146 );
    }

    @Override
    public void apply( EntityLiving entityLiving ) {
        entityLiving.updateMetadata( entityLiving.getMetadata().setFlag( EntityFlag.INVISIBLE, true ) );
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void remove( EntityLiving entityLiving ) {
        entityLiving.updateMetadata( entityLiving.getMetadata().setFlag( EntityFlag.INVISIBLE, false ) );
    }
}
