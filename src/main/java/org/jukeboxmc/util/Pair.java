package org.jukeboxmc.util;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Value
@EqualsAndHashCode
public class Pair<A, B> {

    A first;
    B second;
}
