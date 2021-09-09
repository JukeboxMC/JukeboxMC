package org.jukeboxmc.nbt.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Cloudburst
 * @version 1.0
 */
public class UnmodifiableEntrySet<K, V> implements Set<Map.Entry<K, V>>, Collection<Map.Entry<K, V>> {

    private final Set<? extends Map.Entry<K, V>> entrySet;

    public UnmodifiableEntrySet( Set<? extends Map.Entry<K, V>> entrySet ) {
        this.entrySet = entrySet;
    }

    static <K, V> Consumer<Map.Entry<K, V>> entryConsumer( Consumer<? super Map.Entry<K, V>> action ) {
        return e -> action.accept( new UnmodifiableEntry<>( e ) );
    }

    public void forEach( Consumer<? super Map.Entry<K, V>> action ) {
        Objects.requireNonNull( action );
        this.entrySet.forEach( entryConsumer( action ) );
    }

    public int hashCode() {
        return this.entrySet.hashCode();
    }

    public int size() {
        return this.entrySet.size();
    }

    public boolean isEmpty() {
        return this.entrySet.isEmpty();
    }

    public String toString() {
        return this.entrySet.toString();
    }

    public boolean add( Map.Entry<K, V> e ) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll( Collection<? extends Map.Entry<K, V>> c ) {
        throw new UnsupportedOperationException();
    }

    public boolean remove( Object o ) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll( Collection<?> coll ) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll( Collection<?> coll ) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    static final class UnmodifiableEntrySetSpliterator<K, V>
            implements Spliterator<Map.Entry<K, V>> {
        final Spliterator<Map.Entry<K, V>> s;

        UnmodifiableEntrySetSpliterator( Spliterator<Map.Entry<K, V>> s ) {
            this.s = s;
        }

        @Override
        public boolean tryAdvance( Consumer<? super Map.Entry<K, V>> action ) {
            Objects.requireNonNull( action );
            return s.tryAdvance( entryConsumer( action ) );
        }

        @Override
        public void forEachRemaining( Consumer<? super Map.Entry<K, V>> action ) {
            Objects.requireNonNull( action );
            s.forEachRemaining( entryConsumer( action ) );
        }

        @Override
        public Spliterator<Map.Entry<K, V>> trySplit() {
            Spliterator<Map.Entry<K, V>> split = s.trySplit();
            return split == null
                    ? null
                    : new UnmodifiableEntrySetSpliterator<>( split );
        }

        @Override
        public long estimateSize() {
            return s.estimateSize();
        }

        @Override
        public long getExactSizeIfKnown() {
            return s.getExactSizeIfKnown();
        }

        @Override
        public int characteristics() {
            return s.characteristics();
        }

        @Override
        public boolean hasCharacteristics( int characteristics ) {
            return s.hasCharacteristics( characteristics );
        }

        @Override
        public Comparator<? super Map.Entry<K, V>> getComparator() {
            return s.getComparator();
        }
    }

    @SuppressWarnings ( "unchecked" )
    public Spliterator<Map.Entry<K, V>> spliterator() {
        return new UnmodifiableEntrySetSpliterator<>(
                (Spliterator<Map.Entry<K, V>>) this.entrySet.spliterator() );
    }

    @Override
    public Stream<Map.Entry<K, V>> stream() {
        return StreamSupport.stream( this.spliterator(), false );
    }

    @Override
    public Stream<Map.Entry<K, V>> parallelStream() {
        return StreamSupport.stream( this.spliterator(), true );
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
            private final Iterator<? extends Map.Entry<? extends K, ? extends V>> i = entrySet.iterator();

            public boolean hasNext() {
                return i.hasNext();
            }

            public Map.Entry<K, V> next() {
                return new UnmodifiableEntry<>( i.next() );
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public Object[] toArray() {
        Object[] a = this.entrySet.toArray();
        for ( int i = 0; i < a.length; i++ )
            a[i] = new UnmodifiableEntry<>( (Map.Entry<? extends K, ? extends V>) a[i] );
        return a;
    }

    public <T> T[] toArray( T[] a ) {
        // We don't pass a to c.toArray, to avoid window of
        // vulnerability wherein an unscrupulous multithreaded client
        // could get his hands on raw (unwrapped) Entries from c.
        Object[] arr = this.entrySet.toArray( a.length == 0 ? a : Arrays.copyOf( a, 0 ) );

        for ( int i = 0; i < arr.length; i++ )
            arr[i] = new UnmodifiableEntry<>( (Map.Entry<? extends K, ? extends V>) arr[i] );

        if ( arr.length > a.length )
            return (T[]) arr;

        System.arraycopy( arr, 0, a, 0, arr.length );
        if ( a.length > arr.length )
            a[arr.length] = null;
        return a;
    }

    /**
     * This method is overridden to protect the backing set against
     * an object with a nefarious equals function that senses
     * that the equality-candidate is Map.Entry and calls its
     * setValue method.
     */
    public boolean contains( Object o ) {
        if ( !( o instanceof Map.Entry ) )
            return false;
        return this.entrySet.contains( new UnmodifiableEntry<>( (Map.Entry<?, ?>) o ) );
    }

    /**
     * The next two methods are overridden to protect against
     * an unscrupulous List whose contains(Object o) method senses
     * when o is a Map.Entry, and calls o.setValue.
     */
    public boolean containsAll( Collection<?> coll ) {
        for ( Object e : coll ) {
            if ( !this.contains( e ) ) // Invokes safe contains() above
                return false;
        }
        return true;
    }

    public boolean equals( Object o ) {
        if ( o == this )
            return true;

        if ( !( o instanceof Set ) )
            return false;
        Set<?> s = (Set<?>) o;
        if ( s.size() != this.entrySet.size() )
            return false;
        return this.containsAll( s ); // Invokes safe containsAll() above
    }

    /**
     * This "wrapper class" serves two purposes: it prevents
     * the client from modifying the backing Map, by short-circuiting
     * the setValue method, and it protects the backing Map against
     * an ill-behaved Map.Entry that attempts to modify another
     * Map Entry when asked to perform an equality check.
     */
    private static class UnmodifiableEntry<K, V> implements Map.Entry<K, V> {
        private Map.Entry<? extends K, ? extends V> e;

        UnmodifiableEntry( Map.Entry<? extends K, ? extends V> e ) {
            this.e = Objects.requireNonNull( e );
        }

        public K getKey() {
            return e.getKey();
        }

        public V getValue() {
            return e.getValue();
        }

        public V setValue( V value ) {
            throw new UnsupportedOperationException();
        }

        public int hashCode() {
            return e.hashCode();
        }

        public boolean equals( Object o ) {
            if ( this == o )
                return true;
            if ( !( o instanceof Map.Entry ) )
                return false;
            Map.Entry<?, ?> t = (Map.Entry<?, ?>) o;
            return Objects.equals( e.getKey(), t.getKey() ) &&
                    Objects.equals( e.getValue(), t.getValue() );
        }

        public String toString() {
            return e.toString();
        }
    }
}
