package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPortal extends Block {

    public BlockPortal() {
        super( "minecraft:portal" );
    }

    public void setPortalAxis( PortalAxis portalAxis ) {
        this.setState( "portal_axis", portalAxis.name().toLowerCase() );
    }

    public PortalAxis getPortalAxis() {
        return this.stateExists( "portal_axis" ) ? PortalAxis.valueOf( this.getStringState( "portal_axis" ).toUpperCase() ) : PortalAxis.X;
    }

    public enum PortalAxis {
        UNKNOWN,
        X,
        Z
    }
}
