package org.jukeboxmc.utils;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@RequiredArgsConstructor
public class CopyOnWriteQueue<T> implements Queue<T> {

    private final Set<T> set;
    private Iterator<T> iterator = null;

    @Override
    public int size() {
        return this.set.size();
    }

    @Override
    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    @Override
    public boolean contains( Object o ) {
        return this.set.contains( o );
    }

    @Override
    public Iterator<T> iterator() {
        return this.set.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.set.toArray();
    }

    @Override
    public <T1> T1[] toArray( T1[] a ) {
        return this.set.toArray( a );
    }

    @Override
    public boolean add( T t ) {
        boolean add = this.set.add( t );
        this.iterator = this.iterator();
        return add;
    }

    @Override
    public boolean remove( Object o ) {
        boolean remove = this.set.remove( o );
        this.iterator = this.iterator();
        return remove;
    }

    @Override
    public boolean containsAll( Collection<?> c ) {
        return this.set.containsAll( c );
    }

    @Override
    public boolean addAll( Collection<? extends T> c ) {
        boolean b = this.set.addAll( c );
        this.iterator = this.iterator();
        return b;
    }

    @Override
    public boolean removeAll( Collection<?> c ) {
        boolean b = this.set.removeAll( c );
        this.iterator = this.iterator();
        return b;
    }

    @Override
    public boolean retainAll( Collection<?> c ) {
        boolean b = this.set.retainAll( c );
        this.iterator = this.iterator();
        return b;
    }

    @Override
    public void clear() {
        this.set.clear();
        this.iterator = this.iterator();
    }

    @Override
    public boolean offer( T t ) {
        boolean add = this.set.add( t );
        this.iterator = this.iterator();
        return add;
    }

    @Override
    public T remove() {
        this.iterator = this.iterator();
        if(!this.iterator.hasNext())
            return null;
        T next = this.iterator.next();
        this.set.remove( next );
        return next;
    }

    @Override
    public T poll() {
        return this.remove();
    }

    @Override
    public T element() {
        return this.iterator.next();
    }

    @Override
    public T peek() {
        return this.iterator.next();
    }

}
