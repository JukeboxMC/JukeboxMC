package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEmptyMap extends Item {

    public ItemEmptyMap() {
        super( 505 );
    }

    public void setMapType( MapType mapType ) {
        if ( mapType == MapType.EMPTY ) {
            this.setMeta( 0 );
        } else {
            this.setMeta( 2 );
        }
    }

    public MapType getMapType() {
        if ( this.getMeta() == 0 ) {
            return MapType.EMPTY;
        } else {
            return MapType.EMPTY_LOCATOR;
        }
    }

    public enum MapType {
        EMPTY,
        EMPTY_LOCATOR
    }
}
