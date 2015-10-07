package io.github.benas.xstream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import io.github.benas.xstream.components.Compact;
import io.github.benas.xstream.components.Cut;
import io.github.benas.xstream.components.Dos2Unix;
import io.github.benas.xstream.components.Expand;
import io.github.benas.xstream.components.Fold;
import io.github.benas.xstream.components.Grep;
import io.github.benas.xstream.components.Head;
import io.github.benas.xstream.components.LowerCase;
import io.github.benas.xstream.components.NumberLines;
import io.github.benas.xstream.components.Reverse;
import io.github.benas.xstream.components.Stringify;
import io.github.benas.xstream.components.Tail;
import io.github.benas.xstream.components.Translate;
import io.github.benas.xstream.components.Trim;
import io.github.benas.xstream.components.Truncate;
import io.github.benas.xstream.components.UnExpand;
import io.github.benas.xstream.components.UpperCase;
import io.github.benas.xstream.components.WordCount;

class XStreamImpl<T> implements XStream<T> {
    
    protected Stream<T> stream;

    XStreamImpl(Stream<T> stream) {
        this.stream = stream;
    }
    
    /*
     * Standard Stream API
     */

    @Override
    public XStream<T> filter(Predicate<? super T> predicate) {
        return new XStreamImpl<>(stream.filter(predicate));
    }

    @Override
    public <R> XStream<R> map(Function<? super T, ? extends R> mapper) {
        return new XStreamImpl<>(stream.map(mapper));
    }

    @Override
    public <R> XStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new XStreamImpl<>(stream.flatMap(mapper));
    }

    @Override
    public XStream<T> distinct() {
        return new XStreamImpl<>(stream.distinct());
    }

    @Override
    public XStream<T> sorted() {
        return new XStreamImpl<>(stream.sorted());
    }

    @Override
    public XStream<T> sorted(Comparator<? super T> comparator) {
        return new XStreamImpl<>(stream.sorted(comparator));
    }

    @Override
    public XStream<T> peek(Consumer<? super T> action) {
        return new XStreamImpl<>(stream.peek(action));
    }

    @Override
    public XStream<T> limit(long maxSize) {
        return new XStreamImpl<>(stream.limit(maxSize));
    }

    @Override
    public XStream<T> skip(long n) {
        return new XStreamImpl<>(stream.skip(n));
    }
    
    /*
     * Map to numeric streams
     */

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }
    
    /*
     * Terminal operations
     */

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public XStream<T> sequential() {
        return new XStreamImpl<>(stream.sequential());
    }

    @Override
    public XStream<T> parallel() {
        return new XStreamImpl<>(stream.parallel());
    }

    @Override
    public XStream<T> unordered() {
        return new XStreamImpl<>(stream.unordered());
    }

    @Override
    public XStream<T> onClose(Runnable closeHandler) {
        return new XStreamImpl<>(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }
    
    /*
     * XStream extensions
     */

    @Override
    public XStream<String> compact() {
        return new XStreamImpl<>(Compact.compact().apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> cut(String delimiter, int field) {
        return new XStreamImpl<>(Cut.cut(delimiter, field).apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> dos2unix() {
        return new XStreamImpl<>(Dos2Unix.dos2unix().apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> expand() {
        return new XStreamImpl<>(Expand.expand().apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> fold(int size) {
        return new XStreamImpl<>(Fold.fold(size).apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> grep(String pattern) {
        return new XStreamImpl<>(Grep.grep(pattern).apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<T> head() {
        return new XStreamImpl<>(new Head<T>().apply(stream));
    }

    @Override
    public XStream<T> head(long size) {
        return new XStreamImpl<>(new Head<T>(size).apply(stream));
    }

    @Override
    public XStream<String> lowercase() {
        return new XStreamImpl<>(LowerCase.lowerCase().apply(new Stringify<T>().apply(stream)));
    }
    
    @Override
    public XStream<String> nl() {
        return new XStreamImpl<>(NumberLines.numberLines().apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<T> reverse() {
        return new XStreamImpl<>(new Reverse<T>().apply(stream));
    }

    @Override
    public XStream<T> sort() {
        return sorted();
    }

    @Override
    public XStream<T> sort(Comparator<? super T> comparator) {
        return sorted(comparator);
    }

    @Override
    public XStream<String> str() {
        return new XStreamImpl<>(new Stringify<T>().apply(stream));
    }

    @Override
    public XStream<T> tail() {
        return new XStreamImpl<>(new Tail<T>().apply(stream));
    }

    @Override
    public XStream<T> tail(long size) {
        return new XStreamImpl<>(new Tail<T>(size).apply(stream));
    }
    
    @Override
    public XStream<String> tr(String regexp, String replacement) {
        return new XStreamImpl<>(Translate.translate(regexp, replacement).apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> trim() {
        return new XStreamImpl<>(Trim.trim().apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> trunc(int size) {
        return new XStreamImpl<>(Truncate.truncate(size).apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> unexpand() {
        return new XStreamImpl<>(UnExpand.unexpand().apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<T> uniq() {
        return new XStreamImpl<>(stream.distinct());
    }

    @Override
    public XStream<String> uppercase() {
        return new XStreamImpl<>(UpperCase.uppercase().apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public XStream<String> wc() {
        return wc(WordCount.WordCountOption.L);
    }

    @Override
    public XStream<String> wc(WordCount.WordCountOption wordCountOption) {
        return new XStreamImpl<>(WordCount.wordCount(wordCountOption).apply(new Stringify<T>().apply(stream)));
    }

    @Override
    public void to(PrintWriter printWriter) throws IOException {
        new Stringify<T>().apply(stream).forEachOrdered(printWriter::println);
    }

    @Override
    public <R> XStream<R> pipe(Stage<T, R> stage) {
        return new XStreamImpl<>(stage.apply(stream));
    }

    @Override
    public Stream<T> get() {
        return stream;
    }
    
}