package org.jukeboxmc.crafting.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class PotionMixData {

    // Potion to be put in
    private final int inputId;
    private final int inputMeta;

    // Item to be added to the brewing stand to brew the output potion
    private final int reagentId;
    private final int reagentMeta;

    // Output Potion
    private final int outputId;
    private final int outputMeta;
}
