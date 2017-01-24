package practice.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<T> implements Iterable<T> {
	private Node<T> first;
	private Node<T> last;
	private int n;
	
	public Queue() {
		this.first = null;
		this.last = null;
		this.n = 0;
	}
	
	private static class Node<T> {
		private T item;
		private Node<T> next;
	}
	
	public void enqueue(T item) {
		Node<T> oldLast = this.last;
		this.last = new Node<>();
		last.item = item;
		if (isEmpty()) {
			first = last;
		} else {
			oldLast.next = last;
		}
		this.n++;
	}
	
	public T dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T item = first.item;
		first = first.next;
		n--;
		if (isEmpty()) {
			last = null;
		}
		return item;
	}
	
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return first.item;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return n;
	}
	
	@Override
	public Iterator<T> iterator() {
		return null;
	}
	
	private class ListIterator<T> implements Iterator<T> {
		private Node<T> current;
		
		public ListIterator(Node<T> first) {
			this.current = first;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (current == null) {
				throw new NoSuchElementException();
			}
			T item = current.item;
			current = current.next;
			return item;
		}
		
	}

}
