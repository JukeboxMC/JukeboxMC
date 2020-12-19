package org.jukeboxmc.world;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@AllArgsConstructor ( access = AccessLevel.PRIVATE )
public class GameRules<T> {

    private String name;
    private int type;
    @Setter
    private T value;

    public static GameRules<Boolean> booleanValue( String name, boolean value ) {
        return new GameRules<>( name, 1, value );
    }

    public static GameRules<Integer> intValue( String name, int value ) {
        return new GameRules<>( name, 2, value );
    }

    public static GameRules<Float> floatValue( String name, float value ) {
        return new GameRules<>( name, 3, value );
    }

}
