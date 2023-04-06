package org.jukeboxmc.util;

import org.cloudburstmc.protocol.bedrock.data.defintions.BlockDefinition;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RuntimeBlockDefination implements BlockDefinition {

    private final int runtimeId;

    public RuntimeBlockDefination( int runtimeId ) {
        this.runtimeId = runtimeId;
    }

    @Override
    public int getRuntimeId() {
        return this.runtimeId;
    }
}
