package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPortal;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPortal extends Block {

    public BlockPortal() {
        super( "minecraft:portal" );
    }

    @Override
    public ItemPortal toItem() {
        return new ItemPortal();
    }

    @Override
    public BlockType getType() {
        return BlockType.PORTAL;
    }

    public void setPortalAxis( PortalAxis portalAxis ) {
        this.setState( "portal_axis", portalAxis.name().toLowerCase() );
    }

    public PortalAxis getPortalAxis() {
        return this.stateExists( "portal_axis" ) ? PortalAxis.valueOf( this.getStringState( "portal_axis" ) ) : PortalAxis.X;
    }

    public enum PortalAxis {
        UNKNOWN,
        X,
        Z
    }
}
