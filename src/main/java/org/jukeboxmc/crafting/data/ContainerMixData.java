package org.jukeboxmc.crafting.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ContainerMixData {

    private final int inputId;
    private final int reagentId;
    private final int outputId;

}
