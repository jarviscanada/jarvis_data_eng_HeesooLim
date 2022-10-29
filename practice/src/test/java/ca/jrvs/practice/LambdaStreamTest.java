package ca.jrvs.practice;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class LambdaStreamTest extends TestCase {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }


  public void testCreateStrStream() {
    LambdaStream stream = new LambdaStream();
    Stream<String> newStream = stream.createStrStream("first", "second", "third");
    List<String> list = new ArrayList<>();
    list.add("first");
    list.add("second");
    list.add("third");

    Assert.assertEquals(list, newStream.collect(Collectors.toList()));
  }

  public void testToUpperCase() {
    LambdaStream stream = new LambdaStream();
    Stream<String> newStream = stream.toUpperCase("a", "b", "c");
    List<String> list = new ArrayList<>();
    list.add("A");
    list.add("B");
    list.add("C");

    Assert.assertEquals(list, newStream.collect(Collectors.toList()));
  }

  public void testFilter() {
    LambdaStream stream = new LambdaStream();
    Stream<String> strStream = Stream.of("hello", "dfgdhellosdf", "bye");
    Stream<String> newStream = stream.filter(strStream, ".*hello.*");
    List<String> list = new ArrayList<>();
    list.add("hello");
    list.add("dfgdhellosdf");

    Assert.assertEquals(list, newStream.collect(Collectors.toList()));
  }

  public void testCreateIntStreamArray() {
    LambdaStream stream = new LambdaStream();
    int[] intArr = {1, 2,3};
    IntStream newStream = stream.createIntStream(intArr);
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);

    Assert.assertEquals(list, newStream.boxed().collect(Collectors.toList()));
  }

  public void testCreateIntStreamRange() {
    LambdaStream stream = new LambdaStream();
    IntStream newStream = stream.createIntStream(1, 4);
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);

    Assert.assertEquals(list, newStream.boxed().collect(Collectors.toList()));
  }

  public void testToListIntStream() {
    LambdaStream stream = new LambdaStream();
    IntStream intStream = IntStream.of(1, 2, 3);
    List<Integer> convertedList = stream.toList(intStream);
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);

    Assert.assertEquals(list, convertedList);
  }

  public void testToListStream() {
    LambdaStream stream = new LambdaStream();
    Stream<String> strStream = Stream.of("first", "second", "third");
    List<String> convertedList = stream.toList(strStream);
    List<String> list = new ArrayList<>();
    list.add("first");
    list.add("second");
    list.add("third");

    Assert.assertEquals(list, convertedList);
  }

  public void testSquareRootIntStream() {
    LambdaStream stream = new LambdaStream();
    IntStream intStream = IntStream.of(1, 2, 3);
    List<Double> convertedList = stream.squareRootIntStream(intStream).boxed().collect(Collectors.toList());
    List<Double> list = new ArrayList<>();
    list.add(1.0);
    list.add(4.0);
    list.add(9.0);

    Assert.assertEquals(list, convertedList);
  }

  public void testGetOdd() {
    LambdaStream stream = new LambdaStream();
    IntStream intStream = IntStream.of(1, 2, 3);
    List<Integer> convertedList = stream.getOdd(intStream).boxed().collect(Collectors.toList());
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(3);

    Assert.assertEquals(list, convertedList);
  }
}