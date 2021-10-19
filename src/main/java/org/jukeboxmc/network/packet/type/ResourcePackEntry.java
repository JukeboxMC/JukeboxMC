package org.jukeboxmc.network.packet.type;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class ResourcePackEntry {

    private final String uuid;
    private final String version;

    public ResourcePackEntry(final String uuid, final String version) {
        this.uuid = uuid;
        this.version = version;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getVersion() {
        return this.version;
    }

}
