package org.jukeboxmc.world.generator.populator.biome;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.world.generator.populator.Populator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BiomePopulator {

    private final List<Populator> populators = new ArrayList<>();

    public void addPopulator( Populator populator ) {
        this.populators.add( populator );
    }

    public @NotNull List<Populator> getPopulators() {
        return this.populators;
    }
}
