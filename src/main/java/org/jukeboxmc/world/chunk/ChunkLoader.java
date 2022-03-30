package org.jukeboxmc.world.chunk;

import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface ChunkLoader {

    static ChunkLoader whenSucceed( Consumer<Chunk> consumer) {
        return new ChunkLoader() {
            @Override
            public void chunkLoadCallback(Chunk chunk, boolean success) {
                if(success) consumer.accept(chunk);
            }

            @Override
            public void chunkGenerationCallback(Chunk chunk) {
                consumer.accept(chunk);
            }
        };
    }

    void chunkLoadCallback(Chunk chunk, boolean success);

    void chunkGenerationCallback(Chunk chunk);

}
