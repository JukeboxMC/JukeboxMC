package org.jukeboxmc.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * @author KCodeYT
 * @version 1.0
 */
public final class NonStream {

    private NonStream() {
        throw new UnsupportedOperationException();
    }

    public static <E> boolean noneMatch(@NotNull Iterable<E> iterable, @NotNull Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return false;
        return true;
    }

    public static <E> boolean singleMatch(@NotNull Iterable<E> iterable, @NotNull Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return true;
        return false;
    }

    public static <E> boolean allMatch(@NotNull Iterable<E> iterable, @NotNull Predicate<E> predicate) {
        for(E e : iterable)
            if(!predicate.test(e))
                return false;
        return true;
    }

    public static <E> @NotNull Optional<E> filterOptional(@NotNull Iterable<E> iterable, @NotNull Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return Optional.of(e);
        return Optional.empty();
    }

    public static <E> @Nullable E filter(@NotNull Iterable<E> iterable, @NotNull Predicate<E> predicate) {
        for(E e : iterable)
            if(predicate.test(e))
                return e;
        return null;
    }

    public static <E> @NotNull List<E> filterList(@NotNull Iterable<E> iterable, @NotNull Predicate<E> predicate) {
        final List<E> list = new ArrayList<>();
        for(E e : iterable)
            if(predicate.test(e))
                list.add(e);
        return list;
    }

    public static <E, C extends Collection<R>, R> C filterAndMap(E @NotNull [] array, @NotNull Predicate<E> predicate, @NotNull Function<E, R> mapper, @NotNull CollectionFactory<C, R> factory) {
        final C collection = factory.create();
        for(E e : array)
            if(predicate.test(e))
                collection.add(mapper.apply(e));
        return collection;
    }

    public static <E> @NotNull Optional<E> findFirst(@NotNull Iterable<E> iterable) {
        for(E e : iterable)
            return Optional.of(e);
        return Optional.empty();
    }

    public static <E> int sum(E @NotNull [] array, @NotNull Function<E, Integer> mapper) {
        int sum = 0;
        for(E e : array) sum += mapper.apply(e);
        return sum;
    }

    public static <E, C extends Collection<R>, R> C map(E @NotNull [] array, @NotNull Function<E, R> mapper, @NotNull CollectionFactory<C, R> factory) {
        final C collection = factory.create();
        for(E e : array) collection.add(mapper.apply(e));
        return collection;
    }

    public static <E, C extends Collection<R>, R> C map(@NotNull Iterable<E> iterable, @NotNull Function<E, R> mapper, @NotNull CollectionFactory<C, R> factory) {
        final C collection = factory.create();
        for(E e : iterable) collection.add(mapper.apply(e));
        return collection;
    }

    public static <E, C extends Collection<R>, UC extends Collection<R>, R> UC map(@NotNull Iterable<E> iterable, @NotNull Function<E, R> mapper,
                                                                                   @NotNull CollectionFactory<C, R> factory, @NotNull Function<C, UC> unmodifiableFunc) {
        final C collection = factory.create();
        for(E e : iterable) collection.add(mapper.apply(e));
        return unmodifiableFunc.apply(collection);
    }

    public static <E, R> R[] mapArray(@NotNull Iterable<E> iterable, @NotNull Function<E, R> mapper, @NotNull IntFunction<R[]> function) {
        int counter = 0;
        for(E e : iterable) counter++;

        final R[] array = function.apply(counter);
        int i = 0;
        for(E e : iterable) array[i++] = mapper.apply(e);

        return array;
    }

    public static <E, K, V> Map<K, V> toMap(@NotNull Iterable<E> iterable, @NotNull Function<E, K> keyMapper, @NotNull Function<E, V> valueMapper, @NotNull MapFactory<K, V> mapFactory) {
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
