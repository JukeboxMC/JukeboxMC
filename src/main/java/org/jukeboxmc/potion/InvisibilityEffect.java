package org.jukeboxmc.potion;

import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import org.jetbrains.annotations.NotNull;
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
    public @NotNull EffectType getEffectType() {
        return EffectType.INVISIBILITY;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 127, 131, 146 );
    }

    @Override
    public void apply(@NotNull EntityLiving entityLiving ) {
        entityLiving.updateMetadata( entityLiving.getMetadata().setFlag( EntityFlag.INVISIBLE, true ) );
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void remove(@NotNull EntityLiving entityLiving ) {
        entityLiving.updateMetadata( entityLiving.getMetadata().setFlag( EntityFlag.INVISIBLE, false ) );
    }
}
