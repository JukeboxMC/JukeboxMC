package org.jukeboxmc.block.behavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface Waterlogable {

    default int getWaterLoggingLevel() {
        return 1;
    }
}
