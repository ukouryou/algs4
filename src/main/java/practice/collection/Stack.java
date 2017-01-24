package practice.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<T> implements Iterable<T> {

	// top of stack
	private Node<T> first;
	// size of the stack
	private int n;
	
	public static class Node<T> {
		private T item;
		private Node<T> next;
	}
	
	public Stack() {
		this.first = null;
		n = 0;
	}
	
	public void push(T item) {
		Node<T> oldFirst = first;
		this.first = new Node<>();
		first.item = item;
		this.first.next = oldFirst;
		n++;
	}
	
	public T pop() {
		if (isEmpty()) {
			throw new NoSuchElementException("Stack Underflow");
		}
		T item = first.item;
		this.first = first.next;
		n--;
		return item;
	}
	
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException("Stack Underflow");
		}
		T item = first.item;
		return item;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return n;
	}
	
	
	@Override
	public Iterator<T> iterator() {
		return new ListIterator<T>(first);
	}
	
	public static class ListIterator<T> implements Iterator<T> {
		
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
