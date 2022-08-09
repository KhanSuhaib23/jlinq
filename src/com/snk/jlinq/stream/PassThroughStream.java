package com.snk.jlinq.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

// Native type stream()s are not properly implemented
public abstract class PassThroughStream<T> implements Stream<T> {
    @Override
    public <R> Stream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        return underlyingStream().mapMulti(mapper);
    }

    @Override
    public IntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper) {
        return underlyingStream().mapMultiToInt(mapper);
    }

    @Override
    public LongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper) {
        return underlyingStream().mapMultiToLong(mapper);
    }

    @Override
    public DoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper) {
        return underlyingStream().mapMultiToDouble(mapper);
    }

    @Override
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return underlyingStream().takeWhile(predicate);
    }

    @Override
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        return underlyingStream().dropWhile(predicate);
    }

    @Override
    public List<T> toList() {
        return underlyingStream().toList();
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return underlyingStream().filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return underlyingStream().map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return underlyingStream().mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return underlyingStream().mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return underlyingStream().mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return underlyingStream().flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return underlyingStream().flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return underlyingStream().flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return underlyingStream().flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return underlyingStream().distinct();
    }

    @Override
    public Stream<T> sorted() {
        return underlyingStream().sorted();
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return underlyingStream().sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return underlyingStream().peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return underlyingStream().limit(maxSize);
    }

    @Override
    public Stream<T> skip(long n) {
        return underlyingStream().skip(n);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        underlyingStream().forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        underlyingStream().forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return underlyingStream().toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return underlyingStream().toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return underlyingStream().reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return underlyingStream().reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return underlyingStream().reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return underlyingStream().collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return underlyingStream().collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return underlyingStream().min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return underlyingStream().max(comparator);
    }

    @Override
    public long count() {
        return underlyingStream().count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return underlyingStream().anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return underlyingStream().allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return underlyingStream().noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return underlyingStream().findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return underlyingStream().findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return underlyingStream().iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return underlyingStream().spliterator();
    }

    @Override
    public boolean isParallel() {
        return underlyingStream().isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return underlyingStream().sequential();
    }

    @Override
    public Stream<T> parallel() {
        return underlyingStream().parallel();
    }

    @Override
    public Stream<T> unordered() {
        return underlyingStream().unordered();
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return underlyingStream().onClose(closeHandler);
    }

    @Override
    public void close() {
        underlyingStream().close();
    }

    protected abstract Stream<T> underlyingStream();
}
