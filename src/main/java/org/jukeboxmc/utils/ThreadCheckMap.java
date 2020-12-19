package org.jukeboxmc.utils;

import lombok.AllArgsConstructor;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@AllArgsConstructor
public class ThreadCheckMap<K, E> extends AbstractMap<K, E> {

    private Map<K, E> map;

    @Override
    public Set<Entry<K, E>> entrySet() {
        System.out.println("THREAD " + Thread.currentThread().getName() + "#Id" + Thread.currentThread().getId() + " is using map!");
        return this.map.entrySet();
    }

    @Override
    public E put( K key, E value ) {
        return this.map.put( key, value );
    }


}
