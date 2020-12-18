package org.jukeboxmc.utils;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Pair<A, B> {

    private A first;
    private B second;

    public Pair( A first, B second ) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return this.first;
    }

    public B getSecond() {
        return this.second;
    }
}
