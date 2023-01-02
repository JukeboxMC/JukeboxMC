package org.jukeboxmc.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public final class NonStream {

    private NonStream() {
        throw new UnsupportedOperationException();
    }

    public static <E> boolean noneMatch(Iterable<E> iterable, Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return false;
        return true;
    }

    public static <E> boolean singleMatch(Iterable<E> iterable, Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return true;
        return false;
    }

    public static <E> boolean allMatch(Iterable<E> iterable, Predicate<E> predicate) {
        for(E e : iterable)
            if(!predicate.test(e))
                return false;
        return true;
    }

    public static <E> Optional<E> filterOptional(Iterable<E> iterable, Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return Optional.of(e);
        return Optional.empty();
    }

    public static <E> E filter(Iterable<E> iterable, Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return e;
        return null;
    }

    public static <E> List<E> filterList(Iterable<E> iterable, Predicate<E> predicate) {
        final List<E> list = new ArrayList<>();
        for(E e : iterable)
            if(predicate.test(e))
                list.add(e);
        return list;
    }

    public static <E, C extends Collection<R>, R> C filterAndMap(E[] array, Predicate<E> predicate, Function<E, R> mapper, CollectionFactory<C, R> factory) {
        final C collection = factory.create();
        for(E e : array)
            if(predicate.test(e))
                collection.add(mapper.apply(e));
        return collection;
    }

    public static <E> Optional<E> findFirst(Iterable<E> iterable) {
        for(E e : iterable)
            return Optional.of(e);
        return Optional.empty();
    }

    public static <E> int sum(E[] array, Function<E, Integer> mapper) {
        int sum = 0;
        for(E e : array) sum += mapper.apply(e);
        return sum;
    }

    public static <E, C extends Collection<R>, R> C map(E[] array, Function<E, R> mapper, CollectionFactory<C, R> factory) {
        final C collection = factory.create();
        for(E e : array) collection.add(mapper.apply(e));
        return collection;
    }

    public static <E, C extends Collection<R>, R> C map(Iterable<E> iterable, Function<E, R> mapper, CollectionFactory<C, R> factory) {
        final C collection = factory.create();
        for(E e : iterable) collection.add(mapper.apply(e));
        return collection;
    }

    public static <E, C extends Collection<R>, UC extends Collection<R>, R> UC map(Iterable<E> iterable, Function<E, R> mapper,
                                                                                   CollectionFactory<C, R> factory, Function<C, UC> unmodifiableFunc) {
        final C collection = factory.create();
        for(E e : iterable) collection.add(mapper.apply(e));
        return unmodifiableFunc.apply(collection);
    }

    public static <E, R> R[] mapArray(Iterable<E> iterable, Function<E, R> mapper, IntFunction<R[]> function) {
        int counter = 0;
        for(E e : iterable) counter++;

        final R[] array = function.apply(counter);
        int i = 0;
        for(E e : iterable) array[i++] = mapper.apply(e);

        return array;
    }

    public static <E, K, V> Map<K, V> toMap(Iterable<E> iterable, Function<E, K> keyMapper, Function<E, V> valueMapper, MapFactory<K, V> mapFactory) {
        final Map<K, V> map = mapFactory.create();
        for(E e : iterable) map.put(keyMapper.apply(e), valueMapper.apply(e));
        return map;
    }

    public interface CollectionFactory<C extends Collection<R>, R> {
        C create();
    }

    public interface MapFactory<K, V> {
        Map<K, V> create();
    }

}
