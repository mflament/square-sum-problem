package org.yah.test.ssp;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <a href="https://en.wikipedia.org/wiki/Hamiltonian_path">Hamiltonian path</a> finder.
 */
class PathFinder {

    private final int N;

    PathFinder(int N) {
        this.N = N;
    }

    /**
     * Find a Hamiltonian path if size N starting from given {@link Node}.
     */
    @Nullable
    public Path findPath(Node start) {
        Visit visit = new Visit(N, start);
        return visit.findPath();
    }

    private class Visit {

        private final Path path = new Path();
        private final LinkedList<Iterator<Node>> siblingsStack = new LinkedList<>();
        private final BitSet visited;

        public Visit(int N, Node start) {
            visited = new BitSet(N);
            push(start);
        }

        @Nullable
        public Path findPath() {
            while (path.size() < N && !siblingsStack.isEmpty()) {
                Iterator<Node> siblings = siblingsStack.peekLast();
                if (siblings.hasNext()) {
                    Node sibling = siblings.next();
                    if (!isVisited(sibling))
                        push(sibling);
                } else {
                    poll();
                }
            }
            if (path.size() == N)
                return path;
            return null;
        }

        private void push(Node node) {
            path.add(node);
            visited.set(node.getIndex());
            siblingsStack.offer(node.siblings.iterator());
        }

        public void poll() {
            Node last = path.removeLast();
            visited.clear(last.getIndex());
            siblingsStack.removeLast();
        }

        public boolean isVisited(Node node) {
            return visited.get(node.getIndex());
        }
    }
}
