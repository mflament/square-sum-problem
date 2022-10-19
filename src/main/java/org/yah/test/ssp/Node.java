package org.yah.test.ssp;

import java.util.LinkedList;
import java.util.List;

public class Node {

	public final int value;

	public final List<Node> siblings = new LinkedList<>();

	public Node(int value) {
		super();
		this.value = value;
	}

	public int getIndex() {
		return value - 1;
	}

	public void connect(Node other) {
		if (isSquare(value + other.value)) {
			siblings.add(other);
			other.siblings.add(this);
		}
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	private static boolean isSquare(int v) {
		double s = Math.sqrt(v);
		return s - (int) s == 0;
	}

}
