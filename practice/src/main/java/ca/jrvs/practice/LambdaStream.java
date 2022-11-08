package ca.jrvs.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStream implements LambdaStreamExc {
  @Override
  public Stream<String> createStrStream(String... strings) {
    return Stream.of(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... s) {
    return Stream.of(s).map(String::toUpperCase);
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(s -> s.matches(pattern));
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return IntStream.of(arr);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.range(start, end);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.map(i -> i*i).asDoubleStream();
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(i -> i % 2 == 1);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return s -> System.out.println(prefix + s + suffix);
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    Arrays.stream(messages).forEach(printer);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    intStream.filter(i -> i % 2 == 1).forEach(i -> printer.accept(String.valueOf(i)));
  }
}
