package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

    /**
     * Create a String stream from array
     * note: arbitrary number of value will be stored in an array
     *
     * @param strings list of strings
     * @return a stream of strings
     */
    @Override
    public Stream<String> createStrStream(String ... strings) {
        Stream<String> stringStream;
        if (strings == null) {
            return Stream.empty();
        }

        stringStream = Stream.of(strings);
        return stringStream;
    }

    /**
     * Convert all strings to uppercase please use createStrStream
     *
     * @param strings an array of strings
     * @return a stream of strings all in uppercase
     */
    @Override
    public Stream<String> toUpperCase(String... strings) {
        Stream<String> stringStream = createStrStream(strings);

        return stringStream.map(s -> s.toUpperCase());
    }

    /**
     * filter strings that contains the pattern e.g. filter(stringStream, "a") will return another
     * stream which no element contains a
     *
     * @param stringStream stream of strings
     * @param pattern regex pattern
     * @return stream that has been filtered based on the regex pattern
     */
    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(s -> {
            return s.matches(pattern);
        });
    }

    /**
     * Create a intStream from a arr[]
     *
     * @param arr an array of ints
     * @return an IntStream
     */
    @Override
    public IntStream createIntStream(int[] arr) {
        IntStream iStream;
        if (arr == null) {
            return IntStream.empty();
        }

        iStream = IntStream.of(arr);
        return iStream;
    }

    /**
     * Convert a stream to list
     *
     * @param stream A stream of any type
     * @return A list of the elements in the stream, type of the stream, with the type matching
     * the type of the stream
     */
    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    /**
     * Convert a intStream to list
     *
     * @param intStream stream of ints
     * @return a list of Integers
     */
    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    /**
     * Create a IntStream range from start to end inclusive
     *
     * @param start starting int (inclusive)
     * @param end ending int (inclusive)
     * @return an intStream with the values
     */
    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    /**
     * Convert a intStream to a doubleStream and compute square root of each element
     *
     * @param intStream the stream of ints
     * @return a doubleStream with the square roots of each element computed
     */
    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.asDoubleStream().map(a -> {
            return Math.sqrt(a);
        });
    }

    /**
     * filter all even number and return odd numbers from a intStream
     *
     * @param intStream
     * @return stream of ints with even numbers filtered out
     */
    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(a -> {
            return a % 2 == 1;
        });
    }

    /**
     * Return a lambda function that print a message with a prefix and suffix This lambda can be
     * useful to format logs
     * <p>
     * You will learn: - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig - lambda
     * syntax
     * <p>
     * e.g.
     * LambdaStreamExc lse = new LambdaStreamImp();
     * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
     * printer.accept("Message body");
     * <p>
     * sout: start>Message body<end
     *
     * @param prefix prefix str
     * @param suffix suffix str
     * @return a consumer that prints strings with the prefix and suffix
     */
    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        Consumer<String> lambda = (s) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(prefix);
            sb.append(s);
            sb.append(suffix);
            System.out.println(sb.toString());
        };

        return lambda;
    }

    /**
     * Print each message with a given printer Please use `getLambdaPrinter` method
     * <p>
     * e.g. String[] messages = {"a","b", "c"}; lse.printMessages(messages,
     * lse.getLambdaPrinter("msg:", "!") );
     * <p>
     * sout: msg:a! msg:b! msg:c!
     *
     * @param messages messages to be given prefix and suffix
     * @param printer the printer to be used
     */
    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        createStrStream(messages).forEach(printer);
    }

    /**
     * Print all odd number from a intStream. Please use `createIntStream` and `getLambdaPrinter`
     * methods
     * <p>
     * e.g. lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
     * <p>
     * sout: odd number:1! odd number:3! odd number:5!
     *
     * @param intStream int stream
     * @param printer consumer
     */
    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        Stream<String> stringStream = getOdd(intStream).mapToObj(i -> {
            return Integer.toString(i);
        });
        stringStream.forEach(printer);
    }

    /**
     * Square each number from the input. Please write two solutions and compare difference - using
     * flatMap
     *
     * @param ints list of lists of ints
     * @return stream of all elements squared
     */
    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(list -> list.stream()).map(i -> i * i);
    }
}
