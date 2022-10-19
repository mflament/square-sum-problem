package org.yah.test.ssp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <a href=https://www.youtube.com/watch?v=G1m7goLCJDY>Square sum problem</a> resolver
 */
public class SquareSumProblemResolver {

    private final boolean parallel;

    public SquareSumProblemResolver(boolean parallel) {
        this.parallel = parallel;
    }

    /**
     * @return first combination resolved from 1..N
     */
    public Optional<Path> resolve(int N) {
        List<Node> graph = createGraph(N);
        PathFinder pathFinder = new PathFinder(N);
        Stream<Node> nodes = parallel ? graph.parallelStream() : graph.stream();
        return nodes.map(pathFinder::findPath).filter(Objects::nonNull).findFirst();
    }

    private static List<Node> createGraph(int N) {
        if (N <= 1)
            throw new IllegalArgumentException("N must be > 1" + N);
        List<Node> nodes = new ArrayList<>(N);
        for (int i = 1; i <= N; i++) {
            Node node = new Node(i);
            nodes.forEach(node::connect);
            nodes.add(node);
        }
        return nodes;
    }

    public static void main(String[] args) {
        SquareSumProblemResolver resolver = new SquareSumProblemResolver(true);
        boolean test = true;
        if (test) {
            resolver.resolve(25).ifPresentOrElse(System.out::println, () -> System.out.println("No path"));
        } else {
            for (int N = 2; N <= 32; N++) {
                long start = System.currentTimeMillis();
                boolean resolved = resolver.resolve(N).isPresent();
                long elapsed = System.currentTimeMillis() - start;
                System.out.printf("%-3d : %-10s (%d ms)%n", N, resolved ? "resolved" : "no path", elapsed);
            }
        }
    }
}
