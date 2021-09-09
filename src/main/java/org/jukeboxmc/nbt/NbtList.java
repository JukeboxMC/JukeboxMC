package org.jukeboxmc.nbt;

import java.lang.reflect.Array;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * @author Cloudburst
 * @version 1.0
 */
public class NbtList<E> extends AbstractList<E> {

    public static final NbtList<Void> EMPTY = new NbtList<>( NbtType.END );

    private final NbtType<E> type;
    private final E[] array;
    private transient boolean hashCodeGenerated;
    private transient int hashCode;

    public NbtList( NbtType<E> type, Collection<E> collection ) {
        this.type = requireNonNull( type, "tagClass" );
        E[] array = (E[]) Array.newInstance( type.getTagClass(), collection.size() );
        this.array = collection.toArray( array );
    }

    @SafeVarargs
    public NbtList( NbtType<E> tagClass, E... array ) {
        this.type = requireNonNull( tagClass, "tagClass" );
        this.array = Arrays.copyOf( array, array.length );
    }

    public NbtType<E> getType() {
        return type;
    }

    @Override
    public E get( int index ) {
        if ( index < 0 || index >= array.length ) {
            throw new ArrayIndexOutOfBoundsException( "Expected 0-" + ( array.length - 1 ) + ". Got " + index );
        }
        return NbtUtils.copy( array[index] );
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this )
            return true;
        if ( !( o instanceof List ) )
            return false;

        ListIterator<E> e1 = this.listIterator();
        ListIterator<?> e2 = ( (List<?>) o ).listIterator();
        while ( e1.hasNext() && e2.hasNext() ) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if ( !Objects.deepEquals( o1, o2 ) )
                return false;
        }
        return !( e1.hasNext() || e2.hasNext() );
    }

    @Override
    public int hashCode() {
        if ( this.hashCodeGenerated ) return this.hashCode;
        int result = Objects.hash( super.hashCode(), type );
        result = 31 * result + Arrays.deepHashCode( array );
        this.hashCode = result;
        this.hashCodeGenerated = true;
        return result;
    }

    @Override
    public String toString() {
        Iterator<E> it = this.iterator();
        if ( !it.hasNext() )
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append( '[' ).append( '\n' );
        for ( ; ; ) {
            String string = NbtUtils.toString( it.next() );
            sb.append( NbtUtils.indent( string ) );
            if ( !it.hasNext() )
                return sb.append( '\n' ).append( ']' ).toString();
            sb.append( ',' ).append( '\n' );
        }
    }
}
